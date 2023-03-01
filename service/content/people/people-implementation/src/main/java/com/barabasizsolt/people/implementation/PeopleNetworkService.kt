package com.barabasizsolt.people.implementation

import com.barabasizsolt.people.dto.PeopleListDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface PeopleNetworkService {

    @GET("person/popular")
    suspend fun getPopularPeople(@Query("page") page: Int) : PeopleListDTO
}