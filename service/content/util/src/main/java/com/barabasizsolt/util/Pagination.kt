package com.barabasizsolt.util

import kotlinx.coroutines.flow.MutableStateFlow

abstract class PagingItem {
    abstract val id: String
}

suspend fun pagination(
    refreshType: RefreshType,
    flow: MutableStateFlow<List<PagingItem>>,
    getRemoteContent: suspend (ctr: Int) -> List<PagingItem>,
    counter: Int
): List<PagingItem> = when (refreshType) {
    RefreshType.CACHE_IF_POSSIBLE -> flow.value.ifEmpty {
        getRemoteContent(1).also {
            flow.value = it
        }
    }
    RefreshType.NEXT_PAGE -> getRemoteContent(counter).let {
        val newContent = flow.value + it
        flow.value = newContent
        newContent
    }
    RefreshType.FORCE_REFRESH -> getRemoteContent(1).also {
        flow.value = it
    }
}