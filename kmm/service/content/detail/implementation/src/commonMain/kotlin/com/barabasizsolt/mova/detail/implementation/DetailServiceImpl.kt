package com.barabasizsolt.mova.detail.implementation

import com.barabasizsolt.mova.detail.api.DetailService
import detail.movie.model.MovieDetail

class DetailServiceImpl(private val remoteSource: DetailRemoteSource) : DetailService {

    override suspend fun getMovieDetails(id: Int): MovieDetail = remoteSource.getMovieDetails(id = id)
}