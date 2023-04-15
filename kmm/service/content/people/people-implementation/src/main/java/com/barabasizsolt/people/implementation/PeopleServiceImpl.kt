package com.barabasizsolt.people.implementation

import com.barabasizsolt.pagination.Pager
import com.barabasizsolt.pagination.PagerItem
import com.barabasizsolt.pagination.RefreshType
import com.barabasizsolt.people.api.PeopleService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class PeopleServiceImpl(
    private val remoteSource: PeopleRemoteSource
) : PeopleService {

    private val _popularPeople = MutableStateFlow<List<PagerItem>>(value = emptyList())
    override val popularPeople: Flow<List<PagerItem>> = _popularPeople

    private val pager: Pager = Pager()

    override suspend fun getPopularPeople(refreshType: RefreshType): List<PagerItem> = pager.paginate(
        refreshType = refreshType,
        flow = _popularPeople,
        getRemoteContent = { page -> remoteSource.getPopularPeople(page = page) },
        cacheWithError = false
    )

    override fun clearPopularPeople() {
        _popularPeople.value = emptyList()
    }
}