package com.barabasizsolt.movie.dto

import com.barabasizsolt.api.DataLayerException
import com.barabasizsolt.movie.model.MovieList
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieListResponse(
    @Json(name = "page") val page: Int?,
    @Json(name = "results") val results: List<MovieResponse>?
)

fun MovieListResponse.toModel() : MovieList {
    if (page == null || results == null) {
        throw DataLayerException(message = "MovieDiscoverException: $this")
    }
    return MovieList(
        page = page,
        results = results.map { it.toModel() }
    )
}
