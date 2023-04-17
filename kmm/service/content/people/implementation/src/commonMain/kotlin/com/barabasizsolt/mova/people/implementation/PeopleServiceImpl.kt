package com.barabasizsolt.mova.people.implementation

import com.barabasizsolt.mova.pager.Pager
import com.barabasizsolt.mova.pager.PagerItem
import com.barabasizsolt.mova.pager.RefreshType
import com.barabasizsolt.mova.people.api.PeopleService
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