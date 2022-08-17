package com.barabasizsolt.discover.implementation

import com.barabasizsolt.discover.api.DiscoverService
import com.barabasizsolt.discover.api.model.MovieDiscover
import com.barabasizsolt.discover.api.model.TvSeriesDiscover
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class DiscoverServiceImpl(private val remoteSource: DiscoverRemoteSource) : DiscoverService {

    private val _movies = MutableStateFlow<MovieDiscover?>(value = null)
    override val movies: Flow<MovieDiscover?> = _movies

    private val _tvSeries = MutableStateFlow<TvSeriesDiscover?>(value = null)
    override val tvSeries: Flow<TvSeriesDiscover?> = _tvSeries

    override suspend fun getMovies(): MovieDiscover = remoteSource.getMovies()

    override suspend fun getTvSeries(): TvSeriesDiscover = remoteSource.getTvSeries()
}