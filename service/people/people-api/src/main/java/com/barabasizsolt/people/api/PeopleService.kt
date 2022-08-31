package com.barabasizsolt.people.api

import com.barabasizsolt.people.model.PeopleList
import kotlinx.coroutines.flow.Flow

interface PeopleService {

    val popularPeople: Flow<PeopleList?>

    suspend fun getPopularPeople(page: Int): PeopleList

    fun clearPopularPeople()
}