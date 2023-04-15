package com.barabasizsolt.castCrew.implementation

import com.barabasizsolt.cast_crew.dto.CastCrewListDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface CastCrewNetworkService {

    @GET("movie/{movie_id}/credits")
    suspend fun getCastCrewForMovies(@Path("movie_id") movieId: Int) : CastCrewListDTO

    @GET("tv/{tv_id}/credits")
    suspend fun getCastCrewForTv(@Path("tv_id") tvId: Int) : CastCrewListDTO
}