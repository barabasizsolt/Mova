package com.barabasizsolt.people.api

import com.barabasizsolt.people.model.People
import com.barabasizsolt.util.PagingItem
import com.barabasizsolt.util.RefreshType
import kotlinx.coroutines.flow.Flow

interface PeopleService {

    val popularPeople: Flow<List<PagingItem>>

    suspend fun getPopularPeople(refreshType: RefreshType): List<PagingItem>

    fun clearPopularPeople()
}