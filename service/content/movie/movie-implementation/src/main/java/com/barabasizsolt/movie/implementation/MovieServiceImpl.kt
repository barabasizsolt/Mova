package com.barabasizsolt.movie.implementation

import com.barabasizsolt.movie.api.MovieService
import com.barabasizsolt.movie.model.Movie
import com.barabasizsolt.util.PagingItem
import com.barabasizsolt.util.RefreshType
import com.barabasizsolt.util.pagination
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class MovieServiceImpl(private val remoteSource: MovieRemoteSource) : MovieService {

    private val _popularMovies = MutableStateFlow<List<PagingItem>>(value = emptyList())
    override val popularMovies: Flow<List<PagingItem>> = _popularMovies

    private val _upcomingMovies = MutableStateFlow<List<PagingItem>>(value = emptyList())
    override val upcomingMovies: Flow<List<PagingItem>> = _upcomingMovies

    private val _topRatedMovies = MutableStateFlow<List<PagingItem>>(value = emptyList())
    override val topRatedMovies: Flow<List<PagingItem>> = _topRatedMovies

    private val _nowPlayingMovies = MutableStateFlow<List<PagingItem>>(value = emptyList())
    override val nowPlayingMovies: Flow<List<PagingItem>> = _nowPlayingMovies

    override suspend fun getUpcomingMovies(refreshType: RefreshType): List<PagingItem> = pagination(
        refreshType = refreshType,
        flow = _upcomingMovies,
        getRemoteContent = { page -> remoteSource.getUpcomingMovies(page = page) },
        counter = UPCOMING_MOVIES_CTR++
    )

    override suspend fun getPopularMovies(refreshType: RefreshType): List<PagingItem> = pagination(
        refreshType = refreshType,
        flow = _popularMovies,
        getRemoteContent = { page -> remoteSource.getPopularMovies(page = page) },
        counter = POPULAR_MOVIES_CTR++
    )

    override suspend fun getTopRatedMovies(refreshType: RefreshType): List<PagingItem> = pagination(
        refreshType = refreshType,
        flow = _topRatedMovies,
        getRemoteContent = { page -> remoteSource.getTopRatedMovies(page = page) },
        counter = TOP_RATED_MOVIES_CTR++
    )

    override suspend fun getNowPlayingMovies(refreshType: RefreshType): List<PagingItem> = pagination(
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
        private var UPCOMING_MOVIES_CTR: Int = 1
        private var POPULAR_MOVIES_CTR: Int = 1
        private var NOW_PLAYING_MOVIES_CTR: Int = 1
        private var TOP_RATED_MOVIES_CTR: Int = 1
    }
}