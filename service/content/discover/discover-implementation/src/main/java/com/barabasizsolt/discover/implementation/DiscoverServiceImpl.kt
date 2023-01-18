package com.barabasizsolt.discover.implementation

import com.barabasizsolt.discover.api.DiscoverService
import com.barabasizsolt.util.PagingItem
import com.barabasizsolt.util.RefreshType
import com.barabasizsolt.util.pagination
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class DiscoverServiceImpl(private val remoteSource: DiscoverRemoteSource) : DiscoverService {

    private val _movies = MutableStateFlow<List<PagingItem>>(value = emptyList())
    override val movies: Flow<List<PagingItem>> = _movies

    private val _tvSeries = MutableStateFlow<List<PagingItem>>(value = emptyList())
    override val tvSeries: Flow<List<PagingItem>> = _tvSeries

    override suspend fun getMovies(
        region: List<String>,
        withGenres: List<Int>,
        sortBy: List<String>,
        refreshType: RefreshType
    ): List<PagingItem> = pagination(
        refreshType = refreshType,
        flow = _movies,
        getRemoteContent = {
            page -> remoteSource.getMovies(region = region, withGenres = withGenres, sortBy = sortBy, page = page)
        },
        counter = MOVIES_CTR++
    )

    override suspend fun getTvSeries(
        region: List<String>,
        withGenres: List<Int>,
        sortBy: List<String>,
        refreshType: RefreshType
    ): List<PagingItem> = pagination(
        refreshType = refreshType,
        flow = _tvSeries,
        getRemoteContent = {
            page -> remoteSource.getTvSeries(region = region, withGenres = withGenres, sortBy = sortBy, page = page)
        },
        counter = TV_SERIES_CTR++
    )

    override suspend fun searchMovies(query: String, refreshType: RefreshType): List<PagingItem> = pagination(
        refreshType = refreshType,
        flow = _movies,
        getRemoteContent = { page -> remoteSource.searchMovies(query = query, page = page) },
        counter = MOVIES_SEARCH_CTR++
    )

    override suspend fun searchTvSeries(query: String, refreshType: RefreshType): List<PagingItem> = pagination(
        refreshType = refreshType,
        flow = _tvSeries,
        getRemoteContent = { page -> remoteSource.searchTvSeries(query = query, page = page) },
        counter = TV_SERIES_SEARCH_CTR++
    )

    override fun clearMovies() {
       _movies.value = emptyList()
    }

    override fun clearTvSeries() {
        _tvSeries.value = emptyList()
    }

    companion object {
        private var MOVIES_CTR: Int = 1
        private var MOVIES_SEARCH_CTR: Int = 1
        private var TV_SERIES_CTR: Int = 1
        private var TV_SERIES_SEARCH_CTR: Int = 1
    }
}