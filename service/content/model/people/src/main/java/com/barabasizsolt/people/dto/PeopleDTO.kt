package com.barabasizsolt.people.dto

import com.barabasizsolt.people.model.People
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PeopleDTO(
    @Json(name = "id") val id: String?,
    @Json(name = "adult") val adult: Boolean?,
    @Json(name = "profile_path") val profilePath: String?,
    @Json(name = "name") val name: String?
)

fun PeopleDTO.toModel() : People? {
    if (id == null || adult == null || profilePath == null || name == null){
        return null
    }
    return People(id = id, adult = adult, profilePath = profilePath, name = name)
}