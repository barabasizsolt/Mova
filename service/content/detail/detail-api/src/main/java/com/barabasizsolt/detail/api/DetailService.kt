package com.barabasizsolt.detail.api

import com.barabasizsolt.detail.movie.model.MovieDetail

interface DetailService {

    suspend fun getMovieDetails(id: Int): MovieDetail
}