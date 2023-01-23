package com.barabasizsolt.discover.api

import com.barabasizsolt.util.RefreshType
import com.barabasizsolt.util.pagination.PagingItem
import kotlinx.coroutines.flow.Flow

interface ExploreService {

    val discoverMovies: Flow<List<PagingItem>>

    val searchMovies: Flow<List<PagingItem>>

    val discoverTvSeries: Flow<List<PagingItem>>

    val searchTvSeries: Flow<List<PagingItem>>

    suspend fun getMovies(region: List<String>, withGenres: List<Int>, sortBy: List<String>, refreshType: RefreshType) : List<PagingItem>

    suspend fun getTvSeries(region: List<String>, withGenres: List<Int>, sortBy: List<String>, refreshType: RefreshType) : List<PagingItem>

    suspend fun searchMovies(query: String, refreshType: RefreshType) : List<PagingItem>

    suspend fun searchTvSeries(query: String, refreshType: RefreshType) : List<PagingItem>

    fun clearSearchMovies()

    fun clearSearchTvSeries()
}