package com.barabasizsolt.people.dto

import com.barabasizsolt.api.DataLayerException
import com.barabasizsolt.people.model.People
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PeopleResponse(
    @Json(name = "id") val id: String?,
    @Json(name = "adult") val adult: Boolean?,
    @Json(name = "profile_path") val profilePath: String?,
)

fun PeopleResponse.toModel() : People {
    if (id == null || adult == null || profilePath == null){
        throw DataLayerException(message = "PeopleException: $this")
    }
    return People(id = id, adult = adult, profilePath = profilePath)
}