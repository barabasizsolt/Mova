package com.barabasizsolt.people.implementation

import com.barabasizsolt.people.dto.toModel

class PeopleRemoteSource(private val networkService: PeopleNetworkService) {

    suspend fun getPopularPeople(page: Int) = networkService.getPopularPeople(page = page).toModel()
}