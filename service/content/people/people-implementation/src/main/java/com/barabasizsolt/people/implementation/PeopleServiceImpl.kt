package com.barabasizsolt.people.implementation

import com.barabasizsolt.people.api.PeopleService
import com.barabasizsolt.util.PagingItem
import com.barabasizsolt.util.RefreshType
import com.barabasizsolt.util.pagination
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class PeopleServiceImpl(private val remoteSource: PeopleRemoteSource) : PeopleService {

    private val _popularPeople = MutableStateFlow<List<PagingItem>>(value = emptyList())
    override val popularPeople: Flow<List<PagingItem>> = _popularPeople

    override suspend fun getPopularPeople(refreshType: RefreshType): List<PagingItem> = pagination(
        refreshType = refreshType,
        flow = _popularPeople,
        getRemoteContent = { page -> remoteSource.getPopularPeople(page = page) },
        counter = POPULAR_PEOPLE_CTR++
    )

    override fun clearPopularPeople() {
        _popularPeople.value = emptyList()
    }

    companion object {
        private var POPULAR_PEOPLE_CTR: Int = 1
    }
}