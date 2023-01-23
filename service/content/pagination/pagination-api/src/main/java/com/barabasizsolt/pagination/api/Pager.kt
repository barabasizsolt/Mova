package com.barabasizsolt.pagination.api

import kotlinx.coroutines.flow.MutableStateFlow

interface Pager {

    suspend fun paginate(
        refreshType: RefreshType,
        flow: MutableStateFlow<List<PagerItem>>,
        getRemoteContent: suspend (ctr: Int) -> List<PagerItem>,
        cacheWithError: Boolean = true
    ): List<PagerItem>
}