package com.barabasizsolt.people.api

import com.barabasizsolt.pagination.api.PagerItem
import com.barabasizsolt.pagination.api.RefreshType
import kotlinx.coroutines.flow.Flow

interface PeopleService {

    val popularPeople: Flow<List<PagerItem>>

    suspend fun getPopularPeople(refreshType: RefreshType): List<PagerItem>

    fun clearPopularPeople()
}