package com.barabasizsolt.movie.implementation

import com.barabasizsolt.movie.dto.toModel

class MovieRemoteSource(private val networkService: MovieNetworkService) {

    suspend fun getTrendingMovies(page: Int) = networkService.getTrendingMovies(page = page).toModel()

    suspend fun getUpcomingMovies(page: Int) = networkService.getUpcomingMovies(page = page).toModel()
}