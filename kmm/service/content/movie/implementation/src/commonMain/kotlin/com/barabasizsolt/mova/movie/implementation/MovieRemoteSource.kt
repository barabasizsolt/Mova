package com.barabasizsolt.mova.movie.implementation

import com.barabasizsolt.network.api.BaseHttpClient
import com.barabasizsolt.network.api.get
import movie.dto.MovieListDTO
import movie.dto.toModel

class MovieRemoteSource(private val httpClient: BaseHttpClient) {

    suspend fun getPopularMovies(page: Int) =
        httpClient.get<MovieListDTO>(
            urlString = "movie/popular",
            block = { url { parameters.append(name = "page", page.toString()) } }
        ).toModel()

    suspend fun getUpcomingMovies(page: Int) =
        httpClient.get<MovieListDTO>(
            urlString = "movie/upcoming",
            block = { url { parameters.append(name = "page", page.toString()) } }
        ).toModel()

    suspend fun getTopRatedMovies(page: Int) =
        httpClient.get<MovieListDTO>(
            urlString = "movie/top_rated",
            block = { url { parameters.append(name = "page", page.toString()) } }
        ).toModel()

    suspend fun getNowPlayingMovies(page: Int) =
        httpClient.get<MovieListDTO>(
            urlString = "movie/now_playing",
            block = { url { parameters.append(name = "page", page.toString()) } }
        ).toModel()

    suspend fun getSimilarMovies(id: Int, page: Int) =
        httpClient.get<MovieListDTO>(
            urlString = "movie/$id/similar",
            block = { url { parameters.append(name = "page", page.toString()) } }
        ).toModel()
}