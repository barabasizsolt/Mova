package com.barabasizsolt.castCrew.implementation

import com.barabasizsolt.cast_crew.dto.toModel

class CastCrewRemoteSource(private val networkService: CastCrewNetworkService) {

    suspend fun getCastCrewForMovies(id: Int) = networkService.getCastCrewForMovies(movieId = id).toModel()

    suspend fun getCastCrewForTv(id: Int) = networkService.getCastCrewForTv(tvId = id).toModel()
}