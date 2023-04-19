package com.barabasizsolt.mova.explore.implementation

import com.barabasizsolt.network.api.BaseHttpClient
import com.barabasizsolt.network.api.get
import io.ktor.client.request.parameter
import io.ktor.http.parametersOf
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
            parametersOf(name = "region", values = region)
            parametersOf(name = "with_genres", values = withGenres.map { it.toString() })
            parametersOf(name = "sort_by", values = sortBy)
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
            parametersOf(name = "with_genres", values = withGenres.map { it.toString() })
            parametersOf(name = "sort_by", values = sortBy)
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