package com.barabasizsolt.people.api

import com.barabasizsolt.util.RefreshType
import com.barabasizsolt.util.pagination.PagingItem
import kotlinx.coroutines.flow.Flow

interface PeopleService {

    val popularPeople: Flow<List<PagingItem>>

    suspend fun getPopularPeople(refreshType: RefreshType): List<PagingItem>

    fun clearPopularPeople()
}