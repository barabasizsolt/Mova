package com.barabasizsolt.discover.api

import com.barabasizsolt.movie.model.MovieList
import com.barabasizsolt.tv.modell.TvSeriesList
import kotlinx.coroutines.flow.Flow

interface DiscoverService {

    val movies: Flow<MovieList?>

    val tvSeries: Flow<TvSeriesList?>

    suspend fun getMovies() : MovieList

    suspend fun getTvSeries() : TvSeriesList

    suspend fun searchMovies(query: String) : MovieList

    suspend fun searchTvSeries(query: String) : TvSeriesList

    fun clearMovies()

    fun clearTvSeries()
}