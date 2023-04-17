package com.barabasizsolt.mova.genre.api

import kotlinx.coroutines.flow.Flow

enum class GenreType { MOVIE, TV, JOINT }

interface GenreService {

    val movieGenres: Flow<Map<Long, String>>

    val tvGenres: Flow<Map<Long, String>>

    suspend fun getGenres()
}