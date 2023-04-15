package com.barabasizsolt.video.model

data class Video(
    val id: String,
    val name: String,
    val videoId: String,
    val site: String,
    val type: VideoType
)

enum class VideoType {
    TRAILER, BEHIND_THE_SCENES, CLIP, OTHER
}

fun String.toVideoType() = when (this) {
    "Trailer" -> VideoType.TRAILER
    "Behind the Scenes" -> VideoType.BEHIND_THE_SCENES
    "Clip" -> VideoType.CLIP
    else -> VideoType.OTHER
}