package com.barabasizsolt.genre.dto

import com.barabasizsolt.api.DataLayerException
import com.barabasizsolt.genre.model.Genre
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenreListDTO(
    @Json(name = "genres") val genres: List<GenreDTO>?
)

fun GenreListDTO.toModel() : List<Genre> {
    if (genres == null) {
        throw DataLayerException(message = "GenreListException: $this")
    }
    return genres.mapNotNull { it.toModel() }
}
