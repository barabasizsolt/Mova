package com.barabasizsolt.people.api

import com.barabasizsolt.pagination.PagerItem
import com.barabasizsolt.pagination.RefreshType
import kotlinx.coroutines.flow.Flow

interface PeopleService {

    val popularPeople: Flow<List<PagerItem>>

    suspend fun getPopularPeople(refreshType: RefreshType): List<PagerItem>

    fun clearPopularPeople()
}