package com.barabasizsolt.pagination

interface PagerItem {
    val id: String
}

data class TailItem(
    override val id: String = "tailItem",
    val loadMore: Boolean
): PagerItem

data class ErrorItem(
    override val id: String = "errorItem",
    val errorMessage: String
): PagerItem