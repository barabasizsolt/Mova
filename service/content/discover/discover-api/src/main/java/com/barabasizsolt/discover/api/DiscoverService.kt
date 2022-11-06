package com.barabasizsolt.discover.api

import com.barabasizsolt.movie.model.Movie
import com.barabasizsolt.tv.modell.TvSeries
import kotlinx.coroutines.flow.Flow

interface DiscoverService {

    val movies: Flow<List<Movie>?>

    val tvSeries: Flow<List<TvSeries>?>

    suspend fun getMovies(region: List<String>, withGenres: List<Int>, sortBy: List<String>) : List<Movie>

    suspend fun getTvSeries(region: List<String>, withGenres: List<Int>, sortBy: List<String>) : List<TvSeries>

    suspend fun searchMovies(query: String) : List<Movie>

    suspend fun searchTvSeries(query: String) : List<TvSeries>

    fun clearMovies()

    fun clearTvSeries()
}