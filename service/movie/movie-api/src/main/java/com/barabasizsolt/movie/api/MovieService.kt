package com.barabasizsolt.movie.api

import com.barabasizsolt.movie.model.MovieList
import kotlinx.coroutines.flow.Flow

interface MovieService {

    val trendingMovies: Flow<MovieList?>

    val upcomingMovies: Flow<MovieList?>

    val topRatedMovies: Flow<MovieList?>

    suspend fun getTrendingMovies(page: Int): MovieList

    suspend fun getUpcomingMovies(page: Int): MovieList

    suspend fun getTopRatedMovies(page: Int): MovieList

    fun clearTrendingMovies()

    fun clearUpcomingMovies()

    fun clearTopRatedMovies()
}