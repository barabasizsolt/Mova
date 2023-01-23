package com.barabasizsolt.pagination.implementation

import com.barabasizsolt.pagination.api.ErrorItem
import com.barabasizsolt.pagination.api.Pager
import com.barabasizsolt.pagination.api.PagerItem
import com.barabasizsolt.pagination.api.RefreshType
import com.barabasizsolt.pagination.api.TailItem
import kotlinx.coroutines.flow.MutableStateFlow

class PagerImplementation : Pager {

    override suspend fun paginate(
        refreshType: RefreshType,
        flow: MutableStateFlow<List<PagerItem>>,
        getRemoteContent: suspend (ctr: Int) -> List<PagerItem>,
        cacheWithError: Boolean
    ): List<PagerItem> = when (refreshType) {
        RefreshType.CACHE_IF_POSSIBLE -> flow.value.ifEmpty {
            if (cacheWithError) {
                paginationResult(
                    function = {
                        getRemoteContent(1).also {
                            flow.value = it.appendTailItem()
                        }
                    },
                    fallbackContent = emptyList(),
                    flow = flow,
                    sideEffect = { COUNTER = 2 }
                )
            } else {
                getRemoteContent(1).also {
                    flow.value = it.appendTailItem()
                }
            }

        }
        RefreshType.NEXT_PAGE -> {
            val oldContent = flow.value.take(n = flow.value.size - 1)
            paginationResult(
                function = {
                    getRemoteContent(COUNTER).let {
                        val newContent = oldContent + it.appendTailItem()
                        flow.value = newContent
                        newContent
                    }
                },
                fallbackContent = oldContent,
                flow = flow,
                sideEffect = { COUNTER++ }
            )
        }
        RefreshType.FORCE_REFRESH -> paginationResult(
            function = {
                getRemoteContent(1).also {
                    flow.value = it.appendTailItem()
                }
            },
            fallbackContent = flow.value,
            flow = flow,
            sideEffect = { COUNTER = 2 }
        )
    }

    private inline fun paginationResult(
        function: () -> List<PagerItem>,
        fallbackContent: List<PagerItem>,
        flow: MutableStateFlow<List<PagerItem>>,
        sideEffect: () -> Unit = {}
    ): List<PagerItem> = try {
        function().also { sideEffect() }
    } catch (exception: Exception) {
        flow.value = fallbackContent.filter { it !is ErrorItem && it !is TailItem } + listOf(ErrorItem(errorMessage = exception.message.orEmpty()))
        throw exception
    }

    private fun List<PagerItem>.appendTailItem() = this + listOf(TailItem(loadMore = this.isNotEmpty()))

    companion object {
        private var COUNTER: Int = 1
    }
}