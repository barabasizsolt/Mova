package com.barabasizsolt.movie.implementation

import com.barabasizsolt.movie.api.MovieService
import com.barabasizsolt.movie.model.Movie
import com.barabasizsolt.util.RefreshType
import com.barabasizsolt.util.pagination
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class MovieServiceImpl(private val remoteSource: MovieRemoteSource) : MovieService {

    private val _popularMovies = MutableStateFlow<List<Movie>>(value = emptyList())
    override val popularMovies: Flow<List<Movie>> = _popularMovies

    private val _upcomingMovies = MutableStateFlow<List<Movie>>(value = emptyList())
    override val upcomingMovies: Flow<List<Movie>> = _upcomingMovies

    private val _topRatedMovies = MutableStateFlow<List<Movie>>(value = emptyList())
    override val topRatedMovies: Flow<List<Movie>> = _topRatedMovies

    private val _nowPlayingMovies = MutableStateFlow<List<Movie>>(value = emptyList())
    override val nowPlayingMovies: Flow<List<Movie>> = _nowPlayingMovies

    override suspend fun getUpcomingMovies(): List<Movie> = _upcomingMovies.value.ifEmpty {
        remoteSource.getUpcomingMovies(page = 1).also { _upcomingMovies.value = it }
    }

    override suspend fun getPopularMovies(refreshType: RefreshType): List<Movie> = pagination(
        refreshType = refreshType,
        flow = _popularMovies,
        getRemoteContent = { page -> remoteSource.getPopularMovies(page = page) },
        counter = POPULAR_MOVIES_CTR++
    )

    override suspend fun getTopRatedMovies(refreshType: RefreshType): List<Movie> = pagination(
        refreshType = refreshType,
        flow = _topRatedMovies,
        getRemoteContent = { page -> remoteSource.getTopRatedMovies(page = page) },
        counter = TOP_RATED_MOVIES_CTR++
    )

    override suspend fun getNowPlayingMovies(refreshType: RefreshType): List<Movie> = pagination(
        refreshType = refreshType,
        flow = _nowPlayingMovies,
        getRemoteContent = { page -> remoteSource.getNowPlayingMovies(page = page) },
        counter = NOW_PLAYING_MOVIES_CTR++
    )

    override fun clearPopularMovies() {
        _popularMovies.value = emptyList()
    }

    override fun clearUpcomingMovies() {
        _upcomingMovies.value = emptyList()
    }

    override fun clearTopRatedMovies() {
        _topRatedMovies.value = emptyList()
    }

    override fun clearNowPlayingMovies() {
        _nowPlayingMovies.value = emptyList()
    }

    private

    companion object {
        private var POPULAR_MOVIES_CTR: Int = 1
        private var NOW_PLAYING_MOVIES_CTR: Int = 1
        private var TOP_RATED_MOVIES_CTR: Int = 1
    }
}