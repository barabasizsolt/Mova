package com.barabasizsolt.genre.api

import com.barabasizsolt.genre.model.Genre
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface GenreService {

    val genres: Flow<List<Genre>>

    //context(CoroutineScope)
    suspend fun getGenres()
}