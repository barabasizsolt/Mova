package com.barabasizsolt.mova.explore.implementation

import com.barabasizsolt.network.api.BaseHttpClient
import com.barabasizsolt.network.api.get
import io.ktor.client.request.parameter
import movie.dto.MovieListDTO
import movie.dto.toModel
import movie.model.Movie
import tv.dto.TvSeriesDiscoverDTO
import tv.dto.toModel

class ExploreRemoteSource(private val baseHttpClient: BaseHttpClient) {

    suspend fun getMovies(
        region: List<String>,
        withGenres: List<Int>,
        sortBy: List<String>,
        page: Int,
    ) = baseHttpClient.get<MovieListDTO>(
        urlString = "discover/movie",
        block = {
            parameter(key = "region", value = region)
            parameter(key = "with_genres", value = withGenres)
            parameter(key = "sort_by", value = sortBy)
            parameter(key = "page", value = page)
        }
    ).toModel()

    suspend fun getTvSeries(
        withGenres: List<Int>,
        sortBy: List<String>,
        page: Int
    ) = baseHttpClient.get<TvSeriesDiscoverDTO>(
        urlString = "discover/tv",
        block = {
            parameter(key = "with_genres", value = withGenres)
            parameter(key = "sort_by", value = sortBy)
            parameter(key = "page", value = page)
        }
    ).toModel()

    suspend fun searchMovies(
        query: String,
        page: Int
    ): List<Movie> = baseHttpClient.get<MovieListDTO>(
        urlString = "search/movie",
        block = {
            parameter(key = "query", value = query)
            parameter(key = "page", value = page)
        }
    ).toModel()

    suspend fun searchTvSeries(
        query: String,
        page: Int
    ) = baseHttpClient.get<TvSeriesDiscoverDTO>(
        urlString = "search/tv",
        block = {
            parameter(key = "query", value = query)
            parameter(key = "page", value = page)
        }
    ).toModel()
}