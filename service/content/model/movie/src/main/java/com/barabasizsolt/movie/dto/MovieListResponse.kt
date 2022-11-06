package com.barabasizsolt.movie.dto

import com.barabasizsolt.api.DataLayerException
import com.barabasizsolt.movie.model.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieListResponse(
    @Json(name = "page") val page: Int?,
    @Json(name = "results") val results: List<MovieResponse>?
)

fun MovieListResponse.toModel() : List<Movie> {
    if (page == null || results == null) {
        throw DataLayerException(message = "MovieListException: $this")
    }
    return results.mapNotNull { it.toModel() }
}
