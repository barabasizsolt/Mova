package com.barabasizsolt.people.implementation

import com.barabasizsolt.people.api.PeopleService
import com.barabasizsolt.util.RefreshType
import com.barabasizsolt.util.pagination.Pager
import com.barabasizsolt.util.pagination.PagingItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class PeopleServiceImpl(
    private val remoteSource: PeopleRemoteSource,
    private val pager: Pager
) : PeopleService {

    private val _popularPeople = MutableStateFlow<List<PagingItem>>(value = emptyList())
    override val popularPeople: Flow<List<PagingItem>> = _popularPeople

    override suspend fun getPopularPeople(refreshType: RefreshType): List<PagingItem> = pager.paginate(
        refreshType = refreshType,
        flow = _popularPeople,
        getRemoteContent = { page -> remoteSource.getPopularPeople(page = page) },
        //counter = POPULAR_PEOPLE_CTR,
        //incrementCounter = { POPULAR_PEOPLE_CTR++ },
        cacheWithError = false
    )

    override fun clearPopularPeople() {
        _popularPeople.value = emptyList()
    }

    companion object {
        private var POPULAR_PEOPLE_CTR: Int = 1
    }
}