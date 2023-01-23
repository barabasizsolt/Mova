package com.barabasizsolt.discover.implementation

import com.barabasizsolt.discover.api.ExploreService
import com.barabasizsolt.pagination.api.Pager
import com.barabasizsolt.pagination.api.PagerItem
import com.barabasizsolt.pagination.api.RefreshType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class ExploreServiceImpl(
    private val remoteSource: ExploreRemoteSource,
    private val pager: Pager
) : ExploreService {

    private val _discoverMovies = MutableStateFlow<List<PagerItem>>(value = emptyList())
    override val discoverMovies: Flow<List<PagerItem>> = _discoverMovies

    private val _searchMovies = MutableStateFlow<List<PagerItem>>(value = emptyList())
    override val searchMovies: Flow<List<PagerItem>> = _searchMovies

    private val _discoverTvSeries = MutableStateFlow<List<PagerItem>>(value = emptyList())
    override val discoverTvSeries: Flow<List<PagerItem>> = _discoverTvSeries

    private val _searchTvSeries = MutableStateFlow<List<PagerItem>>(value = emptyList())
    override val searchTvSeries: Flow<List<PagerItem>> = _searchTvSeries

    override suspend fun getMovies(
        region: List<String>,
        withGenres: List<Int>,
        sortBy: List<String>,
        refreshType: RefreshType
    ): List<PagerItem> = pager.paginate(
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
    ): List<PagerItem> = pager.paginate(
        refreshType = refreshType,
        flow = _discoverTvSeries,
        getRemoteContent = {
            page -> remoteSource.getTvSeries(region = region, withGenres = withGenres, sortBy = sortBy, page = page)
        }
    )

    override suspend fun searchMovies(query: String, refreshType: RefreshType): List<PagerItem> = pager.paginate(
        refreshType = refreshType,
        flow = _searchMovies,
        getRemoteContent = { page -> remoteSource.searchMovies(query = query, page = page) }
    )

    override suspend fun searchTvSeries(query: String, refreshType: RefreshType): List<PagerItem> = pager.paginate(
        refreshType = refreshType,
        flow = _searchTvSeries,
        getRemoteContent = { page -> remoteSource.searchTvSeries(query = query, page = page) }
    )

    override fun clearSearchMovies() {
       _searchMovies.value = emptyList()
    }

    override fun clearSearchTvSeries() {
        _searchTvSeries.value = emptyList()
    }
}