package com.barabasizsolt.mova.explore.implementation

import com.barabasizsolt.network.api.BaseHttpClient
import com.barabasizsolt.network.api.get
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
            url {
                parameters.append(name = "page", page.toString())
                if (region.isNotEmpty()) { parameters.appendAll(name = "region", region) }
                if (withGenres.isNotEmpty()) { parameters.appendAll(name = "with_genres", withGenres.map { it.toString() }) }
                if (sortBy.isNotEmpty()) { parameters.appendAll(name = "sort_by", sortBy) }
            }
        }
    ).toModel()

    suspend fun getTvSeries(
        withGenres: List<Int>,
        sortBy: List<String>,
        page: Int
    ) = baseHttpClient.get<TvSeriesDiscoverDTO>(
        urlString = "discover/tv",
        block = {
            url {
                parameters.append(name = "page", page.toString())
                if (withGenres.isNotEmpty()) { parameters.appendAll(name = "with_genres", withGenres.map { it.toString() }) }
                if (sortBy.isNotEmpty()) { parameters.appendAll(name = "sort_by", sortBy) }
            }
        }
    ).toModel()

    suspend fun searchMovies(
        query: String,
        page: Int
    ): List<Movie> = baseHttpClient.get<MovieListDTO>(
        urlString = "search/movie",
        block = {
           url {
               parameters.append(name = "page", page.toString())
               parameters.append(name = "query", query)
           }
        }
    ).toModel()

    suspend fun searchTvSeries(
        query: String,
        page: Int
    ) = baseHttpClient.get<TvSeriesDiscoverDTO>(
        urlString = "search/tv",
        block = {
            url {
                parameters.append(name = "page", page.toString())
                parameters.append(name = "query", query)
            }
        }
    ).toModel()
}