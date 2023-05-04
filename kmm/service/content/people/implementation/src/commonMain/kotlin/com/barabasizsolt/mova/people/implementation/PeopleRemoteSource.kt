package com.barabasizsolt.mova.people.implementation

import com.barabasizsolt.network.api.BaseHttpClient
import com.barabasizsolt.network.api.get
import io.ktor.client.request.parameter
import people.dto.PeopleListDTO
import people.dto.toModel

class PeopleRemoteSource(private val baseHttpClient: BaseHttpClient) {

    suspend fun getPopularPeople(page: Int) =
        baseHttpClient.get<PeopleListDTO>(
            urlString = "person/popular",
            block = { url { parameters.append(name = "page", page.toString()) } }
        ).toModel()

}