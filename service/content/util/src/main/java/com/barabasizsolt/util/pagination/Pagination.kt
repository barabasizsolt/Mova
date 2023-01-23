package com.barabasizsolt.util.pagination

import com.barabasizsolt.util.RefreshType
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.dsl.module

//TODO: [HIGH] move into a separate module

val pagerModule = module { factory<Pager> { PagerImplementation() } }

interface Pager {

    suspend fun paginate(
        refreshType: RefreshType,
        flow: MutableStateFlow<List<PagingItem>>,
        getRemoteContent: suspend (ctr: Int) -> List<PagingItem>,
        cacheWithError: Boolean = true
    ): List<PagingItem>
}

internal class PagerImplementation : Pager {

    override suspend fun paginate(
        refreshType: RefreshType,
        flow: MutableStateFlow<List<PagingItem>>,
        getRemoteContent: suspend (ctr: Int) -> List<PagingItem>,
        cacheWithError: Boolean/* TODO: Used for HomeScreenState, there isn't pagination */
    ): List<PagingItem> = when (refreshType) {
        RefreshType.CACHE_IF_POSSIBLE -> flow.value.ifEmpty {
            // TODO [HIGH] improve it
            COUNTER = 1
            if (cacheWithError) {
                paginationResult(
                    function = {
                        getRemoteContent(COUNTER++).also {
                            flow.value = it.appendTailItem()
                        }
                    },
                    fallbackContent = emptyList(),
                    flow = flow
                )
            } else {
                getRemoteContent(COUNTER++).also {
                    flow.value = it.appendTailItem()
                }
            }

        }
        RefreshType.NEXT_PAGE -> {
            val oldContent = flow.value.take(n = flow.value.size - 1)
            paginationResult(
                function = {
                    getRemoteContent(COUNTER++).let {
                        val newContent = oldContent + it.appendTailItem()
                        flow.value = newContent
                        newContent
                    }
                },
                fallbackContent = oldContent,
                flow = flow
            )
        }
        RefreshType.FORCE_REFRESH -> paginationResult(
            function = {
                COUNTER = 1
                getRemoteContent(COUNTER++).also {
                    flow.value = it.appendTailItem()
                }
            },
            fallbackContent = flow.value,
            flow = flow
        )
    }

    private inline fun paginationResult(
        function: () -> List<PagingItem>,
        fallbackContent: List<PagingItem>,
        flow: MutableStateFlow<List<PagingItem>>
    ): List<PagingItem> = try {
        function()
    } catch (exception: Exception) {
        flow.value = fallbackContent.filter { it !is ErrorItem } + listOf(ErrorItem(errorMessage = exception.message.orEmpty()))
        throw exception
    }

    private fun List<PagingItem>.appendTailItem() = this + listOf(TailItem(loadMore = this.isNotEmpty()))

    companion object {
        private var COUNTER: Int = 1
    }
}