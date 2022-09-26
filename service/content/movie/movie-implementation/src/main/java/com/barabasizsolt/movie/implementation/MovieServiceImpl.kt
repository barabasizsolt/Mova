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

    private val _topRatedMovies = MutableStateFlow<MovieList?>(value = null)
    override val topRatedMovies: Flow<MovieList?> = _topRatedMovies

    private val _nowPlayingMovies = MutableStateFlow<MovieList?>(value = null)
    override val nowPlayingMovies: Flow<MovieList?> = _nowPlayingMovies

    override suspend fun getTrendingMovies(page: Int): MovieList = _trendingMovies.value ?: remoteSource.getTrendingMovies(page = page).also {
        _trendingMovies.value = it
    }

    override suspend fun getUpcomingMovies(page: Int): MovieList = _upcomingMovies.value ?: remoteSource.getUpcomingMovies(page = page).also {
        _upcomingMovies.value = it
    }

    override suspend fun getTopRatedMovies(page: Int): MovieList = _topRatedMovies.value ?: remoteSource.getTopRatedMovies(page = page).also {
        _topRatedMovies.value = it
    }

    override suspend fun getNowPlayingMovies(page: Int): MovieList = _nowPlayingMovies.value ?: remoteSource.getNowPlayingMovies(page = page).also {
        _nowPlayingMovies.value = it
    }
    override fun clearTrendingMovies() {
        _trendingMovies.value = null
    }

    override fun clearUpcomingMovies() {
        _upcomingMovies.value = null
    }

    override fun clearTopRatedMovies() {
        _topRatedMovies.value = null
    }

    override fun clearNowPlayingMovies() {
        _nowPlayingMovies.value = null
    }
}