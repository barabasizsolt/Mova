package com.barabasizsolt.movie.implementation

import com.barabasizsolt.movie.api.MovieService
import com.barabasizsolt.movie.model.MovieList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class MovieServiceImpl(private val remoteSource: MovieRemoteSource) : MovieService {

    private val _trendingMovies = MutableStateFlow<MovieList?>(value = null)
    override val trendingMovies: Flow<MovieList?> = _trendingMovies

    private val _upcomingMovies = MutableStateFlow<MovieList?>(value = null)
    override val upcomingMovies: Flow<MovieList?> = _upcomingMovies

    override suspend fun getTrendingMovies(page: Int): MovieList = remoteSource.getTrendingMovies(page = page).also {
        _trendingMovies.value = it
    }

    override suspend fun getUpcomingMovies(page: Int): MovieList = remoteSource.getUpcomingMovies(page = page).also {
        _upcomingMovies.value = it
    }

    override fun clearTrendingMovies() {
        _trendingMovies.value = null
    }

    override fun clearUpcomingMovies() {
        _upcomingMovies.value = null
    }
}