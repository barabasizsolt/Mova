package com.barabasizsolt.discover.implementation

import com.barabasizsolt.movie.dto.toModel
import com.barabasizsolt.movie.model.Movie
import com.barabasizsolt.tv.dto.toModel

class DiscoverRemoteSource(private val networkService: DiscoverNetworkService) {

    suspend fun getMovies(
        region: List<String>,
        withGenres: List<Int>,
        sortBy: List<String>
    ) = networkService.getMovies(
        region = region,
        withGenres = withGenres,
        sortBy = sortBy
    ).toModel()

    suspend fun getTvSeries(
        region: List<String>,
        withGenres: List<Int>,
        sortBy: List<String>
    ) = networkService.getTvSeries(
        region = region,
        withGenres = withGenres,
        sortBy = sortBy
    ).toModel()

    suspend fun searchMovies(query: String): List<Movie> = networkService.searchMovies(query = query).toModel()

    suspend fun searchTvSeries(query: String) = networkService.searchTvSeries(query = query).toModel()
}