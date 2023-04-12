package com.barabasizsolt.detail.implementation

import com.barabasizsolt.detail.movie.dto.toModel

class DetailRemoteSource(private val networkService: DetailNetworkService) {

    suspend fun getMovieDetails(id: Int) = networkService.getMovieDetails(movieId = id).toModel()
}