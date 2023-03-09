package com.barabasizsolt.genre.implementation

import com.barabasizsolt.genre.api.GenreService
import com.barabasizsolt.genre.api.GenreType
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class GenreServiceImpl(private val remoteSource: GenreRemoteSource) : GenreService {

    private val _movieGenres = MutableStateFlow<Map<Long, String>>(value = emptyMap())
    override val movieGenres: Flow<Map<Long, String>> = _movieGenres

    private val _tvGenres = MutableStateFlow<Map<Long, String>>(value = emptyMap())
    override val tvGenres: Flow<Map<Long, String>> = _tvGenres

    override suspend fun getGenres() = coroutineScope {
        if (_movieGenres.value.isEmpty()) {
            val movieGenres = async { remoteSource.getMovieGenres() }
            _movieGenres.value = movieGenres.await().associateBy({it.id}, {it.name})
        }
        if (_tvGenres.value.isEmpty()) {
            val tvSeriesGenres = async { remoteSource.getTvSeriesGenres() }
            _tvGenres.value = tvSeriesGenres.await().associateBy({it.id}, {it.name})
        }
    }
}