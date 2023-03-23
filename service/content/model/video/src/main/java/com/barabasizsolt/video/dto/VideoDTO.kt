package com.barabasizsolt.video.dto

import com.barabasizsolt.video.model.Video
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideoDTO(
    @Json(name = "id") val id: String?,
    @Json(name = "name") val name: String?,
    @Json(name = "key") val key: String?,
    @Json(name = "site") val site: String?,
)

fun VideoDTO.toModel() : Video? {
    if (
        id == null ||
        name == null ||
        key == null ||
        site == null
    ) {
        return null
    }
    return Video(
        id = id,
        name = name,
        videoId = key,
        site = site
    )
}