package com.barabasizsolt.mova.review.implementation

import com.barabasizsolt.network.api.BaseHttpClient
import com.barabasizsolt.network.api.get
import review.dto.ReviewListDTO
import review.dto.toModel

class ReviewRemoteSource(private val baseHttpClient: BaseHttpClient) {

    suspend fun getReviewsForMovie(
        id: Int,
        page: Int
    ) = baseHttpClient.get<ReviewListDTO>(urlString = "movie/$id/reviews?page=$page").toModel()

    suspend fun getReviewsForTv(
        id: Int,
        page: Int
    ) = baseHttpClient.get<ReviewListDTO>(urlString = "tv/$id/reviews?page=$page").toModel()
}