package com.barabasizsolt.genre.dto

import com.barabasizsolt.genre.model.Genre
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenreDTO(
    @Json(name = "id") val id: Long?,
    @Json(name = "name") val name: String?
)

fun GenreDTO.toModel() : Genre? {
    if (id == null || name == null) return null
    return Genre(id = id, name = name)
}
