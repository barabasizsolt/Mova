package com.barabasizsolt.genre.implementation

import com.barabasizsolt.genre.api.GenreService
import com.barabasizsolt.genre.model.Genre
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class GenreServiceImpl(private val remoteSource: GenreRemoteSource) : GenreService {

    private val _genres = MutableStateFlow<List<Genre>>(value = emptyList())
    override val genres: Flow<List<Genre>> = _genres

    override suspend fun getGenres() = coroutineScope {
        val movieGenres = async { remoteSource.getMovieGenres() }
        val tvSeriesGenres = async { remoteSource.getTvSeriesGenres() }
        _genres.value = movieGenres.await() + tvSeriesGenres.await()
        println("<<Here: ${_genres.value}")
    }
}