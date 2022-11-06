package com.barabasizsolt.discover.implementation

import com.barabasizsolt.discover.api.DiscoverService
import com.barabasizsolt.movie.model.Movie
import com.barabasizsolt.tv.modell.TvSeries
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class DiscoverServiceImpl(private val remoteSource: DiscoverRemoteSource) : DiscoverService {

    private val _movies = MutableStateFlow<List<Movie>?>(value = null)
    override val movies: Flow<List<Movie>?> = _movies

    private val _tvSeries = MutableStateFlow<List<TvSeries>?>(value = null)
    override val tvSeries: Flow<List<TvSeries>?> = _tvSeries

    override suspend fun getMovies(
        region: List<String>,
        withGenres: List<Int>,
        sortBy: List<String>
    ): List<Movie> = remoteSource.getMovies(
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
    ): List<TvSeries> = remoteSource.getTvSeries(
        region = region,
        withGenres = withGenres,
        sortBy = sortBy
    ).also {
        _tvSeries.value = it
    }

    override suspend fun searchMovies(query: String): List<Movie> = remoteSource.searchMovies(query = query).also {
        _movies.value = it
    }

    override suspend fun searchTvSeries(query: String): List<TvSeries> = remoteSource.searchTvSeries(query = query).also {
        _tvSeries.value = it
    }

    override fun clearMovies() {
       _movies.value = null
    }

    override fun clearTvSeries() {
        _tvSeries.value = null
    }
}