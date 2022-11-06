package com.barabasizsolt.movie.implementation

import com.barabasizsolt.movie.dto.toModel

class MovieRemoteSource(private val networkService: MovieNetworkService) {

    suspend fun getPopularMovies(page: Int) = networkService.getPopularMovies(page = page).toModel()

    suspend fun getUpcomingMovies(page: Int) = networkService.getUpcomingMovies(page = page).toModel()

    suspend fun getTopRatedMovies(page: Int) = networkService.getTopRatedMovies(page = page).toModel()

    suspend fun getNowPlayingMovies(page: Int) = networkService.getNowPlayingMovies(page = page).toModel()
}