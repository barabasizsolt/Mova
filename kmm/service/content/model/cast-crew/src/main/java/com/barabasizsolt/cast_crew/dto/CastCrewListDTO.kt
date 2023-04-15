package com.barabasizsolt.cast_crew.dto

import com.barabasizsolt.api.DataLayerException
import com.barabasizsolt.cast_crew.model.CastCrew
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CastCrewListDTO(
    @Json(name = "id") val id: Int?,
    @Json(name = "cast") val cast: List<CastDTO>?,
    @Json(name = "crew") val crew: List<CrewDTO>?,
)

fun CastCrewListDTO.toModel() : CastCrew {
    if (id == null || cast == null || crew == null) {
        throw DataLayerException(message = "CastCrewListException: $this")
    }
    return CastCrew(
        casts = cast.mapNotNull { it.toModel() },
        crews = crew.mapNotNull { it.toModel() }
    )
}