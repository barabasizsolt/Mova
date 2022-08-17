package com.barabasizsolt.discover.implementation.model.response

import com.barabasizsolt.api.DataLayerException
import com.barabasizsolt.discover.api.model.MovieDiscover
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDiscoverResponse(
    @Json(name = "page") val page: Int?,
    @Json(name = "results") val results: List<MovieResponse>?
)

fun MovieDiscoverResponse.toModel() : MovieDiscover {
    if (page == null || results == null) {
        throw DataLayerException(message = "MovieDiscoverException: $this")
    }
    return MovieDiscover(
        page = page,
        results = results.map { it.toModel() }
    )
}
