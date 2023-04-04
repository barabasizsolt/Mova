package com.barabasizsolt.movie.implementation

import com.barabasizsolt.movie.api.MovieService
import com.barabasizsolt.pagination.Pager
import com.barabasizsolt.pagination.PagerItem
import com.barabasizsolt.pagination.RefreshType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class MovieServiceImpl(
    private val remoteSource: MovieRemoteSource
) : MovieService {

    private val _popularMovies = MutableStateFlow<List<PagerItem>>(value = emptyList())
    override val popularMovies: Flow<List<PagerItem>> = _popularMovies

    private val _upcomingMovies = MutableStateFlow<List<PagerItem>>(value = emptyList())
    override val upcomingMovies: Flow<List<PagerItem>> = _upcomingMovies

    private val _topRatedMovies = MutableStateFlow<List<PagerItem>>(value = emptyList())
    override val topRatedMovies: Flow<List<PagerItem>> = _topRatedMovies

    private val _nowPlayingMovies = MutableStateFlow<List<PagerItem>>(value = emptyList())
    override val nowPlayingMovies: Flow<List<PagerItem>> = _nowPlayingMovies

    private val _similarMovies = MutableStateFlow<List<PagerItem>>(value = emptyList())
    override val similarMovies: Flow<List<PagerItem>> = _similarMovies

    private val pagers = List(size = 5) { Pager() }

    override suspend fun getUpcomingMovies(refreshType: RefreshType): List<PagerItem> = pagers[0].paginate(
        refreshType = refreshType,
        flow = _upcomingMovies,
        getRemoteContent = { page -> remoteSource.getUpcomingMovies(page = page) },
        cacheWithError = false
    )

    override suspend fun getPopularMovies(refreshType: RefreshType): List<PagerItem> = pagers[1].paginate(
        refreshType = refreshType,
        flow = _popularMovies,
        getRemoteContent = { page -> remoteSource.getPopularMovies(page = page) },
        cacheWithError = false
    )

    override suspend fun getTopRatedMovies(refreshType: RefreshType): List<PagerItem> = pagers[2].paginate(
        refreshType = refreshType,
        flow = _topRatedMovies,
        getRemoteContent = { page -> remoteSource.getTopRatedMovies(page = page) },
        cacheWithError = false
    )

    override suspend fun getNowPlayingMovies(refreshType: RefreshType): List<PagerItem> = pagers[3].paginate(
        refreshType = refreshType,
        flow = _nowPlayingMovies,
        getRemoteContent = { page -> remoteSource.getNowPlayingMovies(page = page) },
        cacheWithError = false
    )

    override suspend fun getSimilarMovies(id: Int, refreshType: RefreshType): List<PagerItem> = pagers[4].paginate(
        refreshType = refreshType,
        flow = _similarMovies,
        getRemoteContent = { page -> remoteSource.getSimilarMovies(id = id, page = page) },
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

    override fun clearSimilarMovies() {
        _similarMovies.value = emptyList()
    }
}