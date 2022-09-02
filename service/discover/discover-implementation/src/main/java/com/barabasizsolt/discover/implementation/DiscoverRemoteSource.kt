package com.barabasizsolt.discover.implementation

import com.barabasizsolt.movie.dto.toModel
import com.barabasizsolt.tv.dto.toModel

class DiscoverRemoteSource(private val networkService: DiscoverNetworkService) {

    suspend fun getMovies() = networkService.getMovies().toModel()

    suspend fun getTvSeries() = networkService.getTvSeries().toModel()
}