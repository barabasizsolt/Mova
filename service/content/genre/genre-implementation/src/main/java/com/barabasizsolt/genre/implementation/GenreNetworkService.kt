package com.barabasizsolt.genre.implementation

import com.barabasizsolt.genre.dto.GenreListDTO
import retrofit2.http.GET

interface GenreNetworkService {

    @GET("genre/movie/list")
    suspend fun getMovieGenres() : GenreListDTO

    @GET("genre/tv/list")
    suspend fun getTvSeriesGenres() : GenreListDTO
}