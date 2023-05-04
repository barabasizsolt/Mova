package review.model

import com.barabasizsolt.mova.pager.PagerItem

data class Review(
    override val id: String,
    val author: String,
    val authorUsername: String?,
    val authorAvatarPath: String?,
    val content: String,
    val createdAt: String
) : PagerItem
