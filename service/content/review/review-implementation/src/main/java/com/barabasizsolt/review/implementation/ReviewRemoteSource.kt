package com.barabasizsolt.review.implementation

import com.barabasizsolt.review.dto.toModel

class ReviewRemoteSource(private val networkService: ReviewNetworkService) {

    suspend fun getReviewsForMovie(id: Int, page: Int) = networkService.getReviewForMovies(movieId = id, page = page).toModel()

    suspend fun getReviewsForTv(id: Int, page: Int) = networkService.getReviewsForTv(tvId = id, page = page).toModel()
}