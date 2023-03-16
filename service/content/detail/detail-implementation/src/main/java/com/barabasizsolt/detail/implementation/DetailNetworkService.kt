package com.barabasizsolt.detail.implementation

import com.barabasizsolt.detail.movie.dto.MovieDetailDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailNetworkService {

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int) : MovieDetailDTO
}