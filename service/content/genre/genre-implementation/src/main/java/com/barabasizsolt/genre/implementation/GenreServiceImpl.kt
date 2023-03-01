package com.barabasizsolt.genre.implementation

import com.barabasizsolt.genre.api.GenreService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class GenreServiceImpl(private val remoteSource: GenreRemoteSource) : GenreService {

    private val _genres = MutableStateFlow<Map<Long, String>>(value = emptyMap())
    override val genres: Flow<Map<Long, String>> = _genres

    override suspend fun getGenres() = coroutineScope {
        val movieGenres = async { remoteSource.getMovieGenres() }
        val tvSeriesGenres = async { remoteSource.getTvSeriesGenres() }
        _genres.value = (movieGenres.await() + tvSeriesGenres.await()).associateBy({it.id}, {it.name})
    }
}