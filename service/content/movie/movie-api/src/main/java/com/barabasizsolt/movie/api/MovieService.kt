package com.barabasizsolt.movie.api

import com.barabasizsolt.util.RefreshType
import com.barabasizsolt.util.pagination.PagingItem
import kotlinx.coroutines.flow.Flow

interface MovieService {

    val popularMovies: Flow<List<PagingItem>>

    val upcomingMovies: Flow<List<PagingItem>>

    val topRatedMovies: Flow<List<PagingItem>>

    val nowPlayingMovies: Flow<List<PagingItem>>

    suspend fun getUpcomingMovies(refreshType: RefreshType): List<PagingItem>

    suspend fun getPopularMovies(refreshType: RefreshType): List<PagingItem>

    suspend fun getTopRatedMovies(refreshType: RefreshType): List<PagingItem>

    suspend fun getNowPlayingMovies(refreshType: RefreshType): List<PagingItem>

    fun clearPopularMovies()

    fun clearUpcomingMovies()

    fun clearTopRatedMovies()

    fun clearNowPlayingMovies()
}