package com.barabasizsolt.mova.explore.implementation

import com.barabasizsolt.mova.explore.api.ExploreService
import com.barabasizsolt.mova.pager.Pager
import com.barabasizsolt.mova.pager.PagerItem
import com.barabasizsolt.mova.pager.RefreshType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class ExploreServiceImpl(
    private val remoteSource: ExploreRemoteSource
) : ExploreService {

    private val _discoverMovies = MutableStateFlow<List<PagerItem>>(value = emptyList())
    override val discoverMovies: Flow<List<PagerItem>> = _discoverMovies

    private val _searchMovies = MutableStateFlow<List<PagerItem>>(value = emptyList())
    override val searchMovies: Flow<List<PagerItem>> = _searchMovies

    private val _discoverTvSeries = MutableStateFlow<List<PagerItem>>(value = emptyList())
    override val discoverTvSeries: Flow<List<PagerItem>> = _discoverTvSeries

    private val _searchTvSeries = MutableStateFlow<List<PagerItem>>(value = emptyList())
    override val searchTvSeries: Flow<List<PagerItem>> = _searchTvSeries

    private val pagers = List(size = 4) { Pager() }

    override suspend fun getMovies(
        region: List<String>,
        withGenres: List<Int>,
        sortBy: List<String>,
        refreshType: RefreshType
    ): List<PagerItem> = pagers[0].paginate(
        refreshType = refreshType,
        flow = _discoverMovies,
        getRemoteContent = {
            page -> remoteSource.getMovies(region = region, withGenres = withGenres, sortBy = sortBy, page = page)
        }
    )

    override suspend fun getTvSeries(
        withGenres: List<Int>,
        sortBy: List<String>,
        refreshType: RefreshType
    ): List<PagerItem> = pagers[1].paginate(
        refreshType = refreshType,
        flow = _discoverTvSeries,
        getRemoteContent = {
            page -> remoteSource.getTvSeries(withGenres = withGenres, sortBy = sortBy, page = page)
        }
    )

    override suspend fun searchMovies(query: String, refreshType: RefreshType): List<PagerItem> = pagers[2].paginate(
        refreshType = refreshType,
        flow = _searchMovies,
        getRemoteContent = { page -> remoteSource.searchMovies(query = query, page = page) }
    )

    override suspend fun searchTvSeries(query: String, refreshType: RefreshType): List<PagerItem> = pagers[3].paginate(
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