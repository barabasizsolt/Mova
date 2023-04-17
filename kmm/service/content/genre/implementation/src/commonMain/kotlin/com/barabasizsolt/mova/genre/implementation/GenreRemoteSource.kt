package com.barabasizsolt.mova.genre.implementation

import com.barabasizsolt.network.api.BaseHttpClient
import com.barabasizsolt.network.api.get
import genre.dto.GenreListDTO
import genre.dto.toModel

class GenreRemoteSource(private val baseHttpClient: BaseHttpClient) {

    suspend fun getMovieGenres() =
        baseHttpClient.get<GenreListDTO>(urlString = "genre/movie/list").toModel()

    suspend fun getTvSeriesGenres() =
        baseHttpClient.get<GenreListDTO>(urlString = "genre/tv/list").toModel()
}