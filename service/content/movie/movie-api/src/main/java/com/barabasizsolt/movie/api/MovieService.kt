package com.barabasizsolt.movie.api

import com.barabasizsolt.movie.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieService {

    val popularMovies: Flow<List<Movie>>

    val upcomingMovies: Flow<List<Movie>>

    val topRatedMovies: Flow<List<Movie>>

    val nowPlayingMovies: Flow<List<Movie>>

    suspend fun getPopularMovies(refreshType: RefreshType): List<Movie>

    suspend fun getUpcomingMovies(page: Int): List<Movie>

    suspend fun getTopRatedMovies(page: Int): List<Movie>

    suspend fun getNowPlayingMovies(page: Int): List<Movie>

    fun clearPopularMovies()

    fun clearUpcomingMovies()

    fun clearTopRatedMovies()

    fun clearNowPlayingMovies()
}