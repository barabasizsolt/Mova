package com.barabasizsolt.util

import kotlinx.coroutines.flow.MutableStateFlow

suspend fun<T> pagination(
    refreshType: RefreshType,
    flow: MutableStateFlow<List<T>>,
    getRemoteContent: suspend (ctr: Int) -> List<T>,
    counter: Int
): List<T> = when (refreshType) {
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