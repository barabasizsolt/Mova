package com.barabasizsolt.people.dto

import com.barabasizsolt.api.DataLayerException
import com.barabasizsolt.people.model.People
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PeopleListResponse(
    @Json(name = "page") val page: Int?,
    @Json(name = "results") val results: List<PeopleResponse>?
)

fun PeopleListResponse.toModel() : List<People> {
    if (page == null || results == null) {
        throw DataLayerException(message = "PeopleListException: $this")
    }
    return results.mapNotNull { it.toModel() }
}