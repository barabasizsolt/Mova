package com.barabasizsolt.genre.api

import kotlinx.coroutines.flow.Flow

interface GenreService {

    val genres: Flow<Map<Long, String>>

    suspend fun getGenres()
}