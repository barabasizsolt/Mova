package com.barabasizsolt.people.implementation

import com.barabasizsolt.people.api.PeopleService
import com.barabasizsolt.people.model.PeopleList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class PeopleServiceImpl(private val remoteSource: PeopleRemoteSource) : PeopleService {

    private val _popularPeople = MutableStateFlow<PeopleList?>(value = null)
    override val popularPeople: Flow<PeopleList?> = _popularPeople

    override suspend fun getPopularPeople(page: Int): PeopleList = _popularPeople.value ?: remoteSource.getPopularPeople(page = page).also {
        _popularPeople.value = it
    }

    override fun clearPopularPeople() {
        _popularPeople.value = null
    }
}