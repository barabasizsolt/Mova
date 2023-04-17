package com.barabasizsolt.genre.implementation

import com.barabasizsolt.genre.dto.toModel

class GenreRemoteSource(private val networkService: GenreNetworkService) {

    suspend fun getMovieGenres() = networkService.getMovieGenres().toModel()

    suspend fun getTvSeriesGenres() = networkService.getTvSeriesGenres().toModel()
}