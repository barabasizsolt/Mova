package com.barabasizsolt.cast.crew.implementation

import cast_crew.dto.CastCrewListDTO
import cast_crew.dto.toModel
import com.barabasizsolt.network.api.BaseHttpClient
import com.barabasizsolt.network.api.get

class CastCrewRemoteSource(private val baseHttpClient: BaseHttpClient) {

    suspend fun getCastCrewForMovies(id: Int) =
        baseHttpClient.get<CastCrewListDTO>(urlString = "movie/$id/credits").toModel()

    suspend fun getCastCrewForTv(id: Int) =
        baseHttpClient.get<CastCrewListDTO>(urlString = "tv/$id/credits").toModel()
}