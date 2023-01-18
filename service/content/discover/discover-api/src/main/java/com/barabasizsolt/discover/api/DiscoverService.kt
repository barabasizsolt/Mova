package com.barabasizsolt.discover.api

import com.barabasizsolt.util.PagingItem
import com.barabasizsolt.util.RefreshType
import kotlinx.coroutines.flow.Flow

interface DiscoverService {

    val movies: Flow<List<PagingItem>>

    val tvSeries: Flow<List<PagingItem>>

    suspend fun getMovies(region: List<String>, withGenres: List<Int>, sortBy: List<String>, refreshType: RefreshType) : List<PagingItem>

    suspend fun getTvSeries(region: List<String>, withGenres: List<Int>, sortBy: List<String>, refreshType: RefreshType) : List<PagingItem>

    suspend fun searchMovies(query: String, refreshType: RefreshType) : List<PagingItem>

    suspend fun searchTvSeries(query: String, refreshType: RefreshType) : List<PagingItem>

    fun clearMovies()

    fun clearTvSeries()
}