package com.barabasizsolt.mova.people.api

import com.barabasizsolt.mova.pager.PagerItem
import com.barabasizsolt.mova.pager.RefreshType
import kotlinx.coroutines.flow.Flow

interface PeopleService {

    val popularPeople: Flow<List<PagerItem>>

    suspend fun getPopularPeople(refreshType: RefreshType): List<PagerItem>

    fun clearPopularPeople()
}