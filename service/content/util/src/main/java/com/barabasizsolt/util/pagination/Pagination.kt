package com.barabasizsolt.util.pagination

import com.barabasizsolt.util.RefreshType
import kotlinx.coroutines.flow.MutableStateFlow

//TODO [high] handle empty search items

suspend fun pagination(
    refreshType: RefreshType,
    flow: MutableStateFlow<List<PagingItem>>,
    getRemoteContent: suspend (ctr: Int) -> List<PagingItem>,
    counter: Int
): List<PagingItem> = when (refreshType) {
    RefreshType.CACHE_IF_POSSIBLE -> flow.value.ifEmpty {
        getRemoteContent(1).also {
            flow.value = it.appendTailItem(loadMore = true)
        }
    }
    RefreshType.NEXT_PAGE -> getRemoteContent(counter).let {
        val newContent = flow.value.take(n = flow.value.size - 1) + it.appendTailItem(loadMore = it.isNotEmpty())
        flow.value = newContent
        newContent
    }
    RefreshType.FORCE_REFRESH -> getRemoteContent(1).also {
        flow.value = it.appendTailItem(loadMore = true)
    }
}

private fun List<PagingItem>.appendTailItem(loadMore: Boolean) = this + listOf(TailItem(loadMore = loadMore))