package com.barabasizsolt.mova.pager

import kotlinx.coroutines.flow.MutableStateFlow

class Pager(initialValue: Int = 1) {

    private var counter: Int = initialValue

    suspend fun paginate(
        refreshType: RefreshType,
        flow: MutableStateFlow<List<PagerItem>>,
        getRemoteContent: suspend (ctr: Int) -> List<PagerItem>,
        cacheWithError: Boolean = true
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
                    sideEffect = { counter = 2 }
                )
            } else {
                getRemoteContent(1).also {
                    flow.value = it.appendTailItem()
                    counter = 2
                }
            }

        }
        RefreshType.NEXT_PAGE -> {
            val oldContent = flow.value.take(n = flow.value.size - 1)
            paginationResult(
                function = {
                    println("<<CTR: $counter")
                    getRemoteContent(counter).let {
                        val newContent = oldContent + it.appendTailItem()
                        flow.value = newContent
                        newContent
                    }
                },
                fallbackContent = oldContent,
                flow = flow,
                sideEffect = { counter++ }
            )
        }
        RefreshType.FORCE_REFRESH -> paginationResult(
            function = {
                getRemoteContent(1).also {
                    flow.value = it.appendTailItem()
                    counter = 2
                }
            },
            fallbackContent = flow.value,
            flow = flow,
            sideEffect = { counter = 2 }
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
}