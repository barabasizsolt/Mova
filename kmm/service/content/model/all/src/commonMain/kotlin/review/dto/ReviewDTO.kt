package review.dto

import review.model.Review
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewDTO(
    @SerialName(value = "id") val id: String?,
    @SerialName(value = "author") val author: String?,
    @SerialName(value = "author_details") val authorDetails: AuthorDTO?,
    @SerialName(value = "content") val content: String?,
    @SerialName(value = "created_at") val createdAt: String?,
)

@Serializable
data class AuthorDTO(
    @SerialName(value = "username") val username: String?,
    @SerialName(value = "avatar_path") val avatarPath: String?,
)

fun ReviewDTO.toModel() : Review? {
    if (
        id == null ||
        author == null ||
        authorDetails == null ||
        content == null ||
        createdAt == null
    ) {
        return null
    }
    return Review(
        id = id,
        author = author,
        authorUsername = authorDetails.username,
        authorAvatarPath = authorDetails.avatarPath,
        content = content,
        createdAt = createdAt
    )
}