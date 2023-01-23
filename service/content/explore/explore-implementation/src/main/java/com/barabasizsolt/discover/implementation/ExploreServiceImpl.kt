package com.barabasizsolt.discover.implementation

import com.barabasizsolt.discover.api.ExploreService
import com.barabasizsolt.util.RefreshType
import com.barabasizsolt.util.pagination.Pager
import com.barabasizsolt.util.pagination.PagingItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class ExploreServiceImpl(
    private val remoteSource: ExploreRemoteSource,
    private val pager: Pager
) : ExploreService {

    private val _discoverMovies = MutableStateFlow<List<PagingItem>>(value = emptyList())
    override val discoverMovies: Flow<List<PagingItem>> = _discoverMovies

    private val _searchMovies = MutableStateFlow<List<PagingItem>>(value = emptyList())
    override val searchMovies: Flow<List<PagingItem>> = _searchMovies

    private val _discoverTvSeries = MutableStateFlow<List<PagingItem>>(value = emptyList())
    override val discoverTvSeries: Flow<List<PagingItem>> = _discoverTvSeries

    private val _searchTvSeries = MutableStateFlow<List<PagingItem>>(value = emptyList())
    override val searchTvSeries: Flow<List<PagingItem>> = _searchTvSeries

    override suspend fun getMovies(
        region: List<String>,
        withGenres: List<Int>,
        sortBy: List<String>,
        refreshType: RefreshType
    ): List<PagingItem> = pager.paginate(
        refreshType = refreshType,
        flow = _discoverMovies,
        getRemoteContent = {
            page -> remoteSource.getMovies(region = region, withGenres = withGenres, sortBy = sortBy, page = page)
        }
    )

    override suspend fun getTvSeries(
        region: List<String>,
        withGenres: List<Int>,
        sortBy: List<String>,
        refreshType: RefreshType
    ): List<PagingItem> = pager.paginate(
        refreshType = refreshType,
        flow = _discoverTvSeries,
        getRemoteContent = {
            page -> remoteSource.getTvSeries(region = region, withGenres = withGenres, sortBy = sortBy, page = page)
        }
    )

    override suspend fun searchMovies(query: String, refreshType: RefreshType): List<PagingItem> = pager.paginate(
        refreshType = refreshType,
        flow = _discoverMovies,
        getRemoteContent = { page -> remoteSource.searchMovies(query = query, page = page) }
    )

    override suspend fun searchTvSeries(query: String, refreshType: RefreshType): List<PagingItem> = pager.paginate(
        refreshType = refreshType,
        flow = _discoverTvSeries,
        getRemoteContent = { page -> remoteSource.searchTvSeries(query = query, page = page) }
    )

    override fun clearSearchMovies() {
       _searchMovies.value = emptyList()
    }

    override fun clearSearchTvSeries() {
        _searchTvSeries.value = emptyList()
    }
}