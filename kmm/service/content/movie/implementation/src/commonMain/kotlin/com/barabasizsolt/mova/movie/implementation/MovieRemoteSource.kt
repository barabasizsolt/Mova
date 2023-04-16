package com.barabasizsolt.mova.movie.implementation

import com.barabasizsolt.network.api.BaseHttpClient
import com.barabasizsolt.network.api.get
import movie.dto.MovieListDTO
import movie.dto.toModel

class MovieRemoteSource(private val httpClient: BaseHttpClient) {

    suspend fun getPopularMovies(page: Int) =
        httpClient.get<MovieListDTO>(urlString = "movie/popular?page=$page").toModel()

    suspend fun getUpcomingMovies(page: Int) =
        httpClient.get<MovieListDTO>(urlString = "movie/upcoming?page=$page").toModel()

    suspend fun getTopRatedMovies(page: Int) =
        httpClient.get<MovieListDTO>(urlString = "discover/movie?page=$page").toModel()

    suspend fun getNowPlayingMovies(page: Int) =
        httpClient.get<MovieListDTO>(urlString = "discover/movie?page=$page").toModel()

    suspend fun getSimilarMovies(id: Int, page: Int) =
        httpClient.get<MovieListDTO>(urlString = "movie/$id/similar?page=$page").toModel()
}