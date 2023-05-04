package video.dto

import video.model.Video
import video.model.toVideoType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoDTO(
    @SerialName(value = "id") val id: String?,
    @SerialName(value = "name") val name: String?,
    @SerialName(value = "key") val key: String?,
    @SerialName(value = "site") val site: String?,
    @SerialName(value = "type") val type: String?
)

fun VideoDTO.toModel() : Video? {
    if (
        id == null ||
        name == null ||
        key == null ||
        site == null ||
        type == null
    ) {
        return null
    }
    return Video(
        id = id,
        name = name,
        videoId = key,
        site = site,
        type = type.toVideoType()
    )
}