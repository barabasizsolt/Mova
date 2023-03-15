package com.barabasizsolt.video.dto

import com.barabasizsolt.api.DataLayerException
import com.barabasizsolt.video.model.Video
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideoListDTO(
    @Json(name = "id") val id: Int?,
    @Json(name = "results") val results: List<VideoDTO>?
)

fun VideoListDTO.toModel() : List<Video> {
    if (id == null || results == null) {
        throw DataLayerException(message = "VideoListException: $this")
    }
    return results.mapNotNull { it.toModel() }
}