package com.barabasizsolt.detail.implementation

import com.barabasizsolt.detail.api.DetailService
import com.barabasizsolt.detail.movie.model.MovieDetail

class DetailServiceImpl(private val remoteSource: DetailRemoteSource) : DetailService {

    override suspend fun getMovieDetails(id: Int): MovieDetail = remoteSource.getMovieDetails(id = id)
}