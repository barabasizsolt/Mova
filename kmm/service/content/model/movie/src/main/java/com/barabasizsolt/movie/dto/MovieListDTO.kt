package com.barabasizsolt.movie.dto

import com.barabasizsolt.api.DataLayerException
import com.barabasizsolt.movie.model.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieListDTO(
    @SerialName(value = "page") val page: Int?,
    @SerialName(value = "results") val results: List<MovieDTO>?,
    @SerialName(value = "total_pages") val totalPages: Int?,
    @SerialName(value = "total_results") val totalResults: Int?,
)

fun MovieListDTO.toModel() : List<Movie> {
    if (page == null || results == null) {
        throw DataLayerException(message = "MovieListException: $this")
    }
    return results.mapNotNull { it.toModel() }
}
