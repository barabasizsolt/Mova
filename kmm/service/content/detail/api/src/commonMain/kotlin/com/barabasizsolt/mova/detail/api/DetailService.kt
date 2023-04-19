package com.barabasizsolt.mova.detail.api

import detail.movie.model.MovieDetail

interface DetailService {

    suspend fun getMovieDetails(id: Int): MovieDetail
}