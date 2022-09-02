package com.barabasizsolt.discover.implementation

import com.barabasizsolt.discover.api.DiscoverService
import com.barabasizsolt.movie.model.MovieList
import com.barabasizsolt.tv.modell.TvSeriesList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class DiscoverServiceImpl(private val remoteSource: DiscoverRemoteSource) : DiscoverService {

    private val _movies = MutableStateFlow<MovieList?>(value = null)
    override val movies: Flow<MovieList?> = _movies

    private val _tvSeries = MutableStateFlow<TvSeriesList?>(value = null)
    override val tvSeries: Flow<TvSeriesList?> = _tvSeries

    override suspend fun getMovies(): MovieList = remoteSource.getMovies().also {
        _movies.value = it
    }

    override suspend fun getTvSeries(): TvSeriesList = remoteSource.getTvSeries().also {
        _tvSeries.value = it
    }

    override fun clearMovies() {
       _movies.value = null
    }

    override fun clearTvSeries() {
        _tvSeries.value = null
    }
}