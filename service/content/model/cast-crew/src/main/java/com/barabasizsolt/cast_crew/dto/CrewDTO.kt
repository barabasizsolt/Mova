package com.barabasizsolt.cast_crew.dto

import com.barabasizsolt.cast_crew.model.Crew
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CrewDTO(
    @Json(name = "id") val id: Int?,
    @Json(name = "name") val name: String?,
    @Json(name = "known_for_department") val knownForDepartment: String?,
    @Json(name = "profile_path") val profilePath: String?,
    @Json(name = "job") val job: String?
)

fun CrewDTO.toModel() : Crew? {
    if (
        id == null ||
        name == null ||
        knownForDepartment == null ||
        job == null
    ) {
        return null
    }
    return Crew(
        id = id.toString(),
        name = name,
        knownForDepartment = knownForDepartment,
        profilePath = profilePath,
        job = job
    )
}