package com.barabasizsolt.util.pagination

data class TailItem(
    override val id: String = "tailItem",
    val loadMore: Boolean
): PagingItem