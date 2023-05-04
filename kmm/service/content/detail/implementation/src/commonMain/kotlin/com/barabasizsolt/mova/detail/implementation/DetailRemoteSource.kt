package com.barabasizsolt.mova.detail.implementation

import com.barabasizsolt.network.api.BaseHttpClient
import com.barabasizsolt.network.api.get
import detail.movie.dto.MovieDetailDTO
import detail.movie.dto.toModel

class DetailRemoteSource(private val baseHttpClient: BaseHttpClient) {

    suspend fun getMovieDetails(id: Int) =
        baseHttpClient.get<MovieDetailDTO>(urlString = "movie/$id").toModel()
}