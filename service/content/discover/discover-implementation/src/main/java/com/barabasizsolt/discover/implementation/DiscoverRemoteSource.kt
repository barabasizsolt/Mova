package com.barabasizsolt.discover.implementation

import com.barabasizsolt.movie.dto.toModel
import com.barabasizsolt.movie.model.Movie
import com.barabasizsolt.tv.dto.toModel

class DiscoverRemoteSource(private val networkService: DiscoverNetworkService) {

    suspend fun getMovies(
        region: List<String>,
        withGenres: List<Int>,
        sortBy: List<String>,
        page: Int,
    ) = networkService.getMovies(
        region = region,
        withGenres = withGenres,
        sortBy = sortBy,
        page = page
    ).toModel()

    suspend fun getTvSeries(
        region: List<String>,
        withGenres: List<Int>,
        sortBy: List<String>,
        page: Int
    ) = networkService.getTvSeries(
        region = region,
        withGenres = withGenres,
        sortBy = sortBy,
        page = page
    ).toModel()

    suspend fun searchMovies(query: String, page: Int): List<Movie> =
        networkService.searchMovies(query = query, page = page).toModel()

    suspend fun searchTvSeries(query: String, page: Int) =
        networkService.searchTvSeries(query = query, page = page).toModel()
}