package com.barabasizsolt.util.pagination

import com.barabasizsolt.util.RefreshType
import kotlinx.coroutines.flow.MutableStateFlow

suspend fun pagination(
    refreshType: RefreshType,
    flow: MutableStateFlow<List<PagingItem>>,
    getRemoteContent: suspend (ctr: Int) -> List<PagingItem>,
    counter: Int,
    incrementCounter: () -> Unit
): List<PagingItem> = when (refreshType) {
    RefreshType.CACHE_IF_POSSIBLE -> flow.value.ifEmpty {
        paginationResult(
            function = { getRemoteContent(1).also { flow.value = it.appendTailItem() } },
            fallbackContent = emptyList(),
            flow = flow
        )
    }
    RefreshType.NEXT_PAGE -> {
        val oldContent = flow.value.take(n = flow.value.size - 1)
        paginationResult(
            function = {
                getRemoteContent(counter).let {
                    incrementCounter()
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
        function = { getRemoteContent(1).also { flow.value = it.appendTailItem() } },
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