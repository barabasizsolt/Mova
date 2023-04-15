package com.barabasizsolt.cast_crew.dto

import com.barabasizsolt.cast_crew.model.Cast
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CastDTO(
    @Json(name = "id") val id: Int?,
    @Json(name = "name") val name: String?,
    @Json(name = "known_for_department") val knownForDepartment: String?,
    @Json(name = "profile_path") val profilePath: String?
)

fun CastDTO.toModel() : Cast? {
    if (
        id == null ||
        name == null ||
        knownForDepartment == null
    ) {
        return null
    }
    return Cast(
        id = id.toString(),
        name = name,
        knownForDepartment = knownForDepartment,
        profilePath = profilePath
    )
}