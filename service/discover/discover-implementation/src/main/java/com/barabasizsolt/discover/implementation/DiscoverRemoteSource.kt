package com.barabasizsolt.discover.implementation

import com.barabasizsolt.discover.implementation.model.response.toModel

class DiscoverRemoteSource(private val networkService: DiscoverNetworkService) {

    suspend fun getMovies() = networkService.getMovies()
        .toModel()

    suspend fun getTvSeries() = networkService.getTvSeries().toModel()
}