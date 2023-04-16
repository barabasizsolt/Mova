package com.barabasizsolt.movie.implementation

import com.barabasizsolt.api.BaseHttpClient
import com.barabasizsolt.api.get
import com.barabasizsolt.movie.dto.MovieListDTO
import com.barabasizsolt.movie.dto.toModel
import io.ktor.client.call.body
import io.ktor.client.request.get

class MovieRemoteSource(private val httpClient: BaseHttpClient) {

    suspend fun getPopularMovies(page: Int) =
        httpClient.get<MovieListDTO>(urlString = "movie/popular?page=$page").toModel()
        //networkService.getPopularMovies(page = page).toModel()

    suspend fun getUpcomingMovies(page: Int) =
        httpClient.get<MovieListDTO>(urlString = "movie/upcoming?page=$page").toModel()
        //networkService.getUpcomingMovies(page = page).toModel()

    suspend fun getTopRatedMovies(page: Int) =
        httpClient.get<MovieListDTO>(urlString = "discover/movie?page=$page").toModel()
        //networkService.getTopRatedMovies(page = page).toModel()

    suspend fun getNowPlayingMovies(page: Int) =
        httpClient.get<MovieListDTO>(urlString = "discover/movie?page=$page").toModel()
        //networkService.getNowPlayingMovies(page = page).toModel()

    suspend fun getSimilarMovies(id: Int, page: Int) =
        httpClient.get<MovieListDTO>(urlString = "discover/movie?page=$page").toModel()
        //networkService.getSimilarMovies(movieId = id, page = page).toModel()
}