package com.barabasizsolt.detail.api

import com.barabasizsolt.detail.movie.model.MovieDetail

interface DetailService {

    fun getMovieDetails(id: String): MovieDetail
}