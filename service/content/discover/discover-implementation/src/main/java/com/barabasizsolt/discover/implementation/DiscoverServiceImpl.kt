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

    override suspend fun getMovies(
        region: List<String>,
        withGenres: List<Int>,
        sortBy: List<String>
    ): MovieList = remoteSource.getMovies(
        region = region,
        withGenres = withGenres,
        sortBy = sortBy
    ).also {
        _movies.value = it
    }

    override suspend fun getTvSeries(
        region: List<String>,
        withGenres: List<Int>,
        sortBy: List<String>
    ): TvSeriesList = remoteSource.getTvSeries(
        region = region,
        withGenres = withGenres,
        sortBy = sortBy
    ).also {
        _tvSeries.value = it
    }

    override suspend fun searchMovies(query: String): MovieList = remoteSource.searchMovies(query = query).also {
        _movies.value = it
    }

    override suspend fun searchTvSeries(query: String): TvSeriesList = remoteSource.searchTvSeries(query = query).also {
        _tvSeries.value = it
    }

    override fun clearMovies() {
       _movies.value = null
    }

    override fun clearTvSeries() {
        _tvSeries.value = null
    }
}