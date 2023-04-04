package com.barabasizsolt.review.dto

import com.barabasizsolt.review.model.Review
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewDTO(
    @Json(name = "id") val id: String?,
    @Json(name = "author") val author: String?,
    @Json(name = "author_details") val authorDetails: AuthorDTO?,
    @Json(name = "content") val content: String?,
    @Json(name = "created_at") val createdAt: String?,
)

@JsonClass(generateAdapter = true)
data class AuthorDTO(
    @Json(name = "username") val username: String?,
    @Json(name = "avatar_path") val avatarPath: String?,
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