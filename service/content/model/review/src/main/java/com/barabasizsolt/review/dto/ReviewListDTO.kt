package com.barabasizsolt.review.dto

import com.barabasizsolt.api.DataLayerException
import com.barabasizsolt.review.model.Review
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewListDTO(
    @Json(name = "page") val page: Int?,
    @Json(name = "results") val results: List<ReviewDTO>?
)

fun ReviewListDTO.toModel() : List<Review> {
    if (page == null || results == null) {
        throw DataLayerException(message = "ReviewListException: $this")
    }
    return results.mapNotNull { it.toModel() }
}