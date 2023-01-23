package com.barabasizsolt.movie.implementation

import com.barabasizsolt.movie.api.MovieService
import com.barabasizsolt.util.RefreshType
import com.barabasizsolt.util.pagination.Pager
import com.barabasizsolt.util.pagination.PagingItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class MovieServiceImpl(
    private val remoteSource: MovieRemoteSource,
    private val pager: Pager
) : MovieService {

    private val _popularMovies = MutableStateFlow<List<PagingItem>>(value = emptyList())
    override val popularMovies: Flow<List<PagingItem>> = _popularMovies

    private val _upcomingMovies = MutableStateFlow<List<PagingItem>>(value = emptyList())
    override val upcomingMovies: Flow<List<PagingItem>> = _upcomingMovies

    private val _topRatedMovies = MutableStateFlow<List<PagingItem>>(value = emptyList())
    override val topRatedMovies: Flow<List<PagingItem>> = _topRatedMovies

    private val _nowPlayingMovies = MutableStateFlow<List<PagingItem>>(value = emptyList())
    override val nowPlayingMovies: Flow<List<PagingItem>> = _nowPlayingMovies

    override suspend fun getUpcomingMovies(refreshType: RefreshType): List<PagingItem> = pager.paginate(
        refreshType = refreshType,
        flow = _upcomingMovies,
        getRemoteContent = { page -> remoteSource.getUpcomingMovies(page = page) },
        cacheWithError = false
    )

    override suspend fun getPopularMovies(refreshType: RefreshType): List<PagingItem> = pager.paginate(
        refreshType = refreshType,
        flow = _popularMovies,
        getRemoteContent = { page -> remoteSource.getPopularMovies(page = page) },
        cacheWithError = false
    )

    override suspend fun getTopRatedMovies(refreshType: RefreshType): List<PagingItem> = pager.paginate(
        refreshType = refreshType,
        flow = _topRatedMovies,
        getRemoteContent = { page -> remoteSource.getTopRatedMovies(page = page) },
        cacheWithError = false
    )

    override suspend fun getNowPlayingMovies(refreshType: RefreshType): List<PagingItem> = pager.paginate(
        refreshType = refreshType,
        flow = _nowPlayingMovies,
        getRemoteContent = { page -> remoteSource.getNowPlayingMovies(page = page) },
        cacheWithError = false
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
}