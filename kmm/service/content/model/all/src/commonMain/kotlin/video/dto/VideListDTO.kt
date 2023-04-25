package video.dto

import DataLayerException
import video.model.Video
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoListDTO(
    @SerialName(value = "id") val id: Int?,
    @SerialName(value = "results") val results: List<VideoDTO>?
)

fun VideoListDTO.toModel() : List<Video> {
    if (id == null || results == null) {
        throw DataLayerException(message = "VideoListException: $this")
    }
    return results.mapNotNull { it.toModel() }
}