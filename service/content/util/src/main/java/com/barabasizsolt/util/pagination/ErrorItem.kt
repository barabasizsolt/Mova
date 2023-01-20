package com.barabasizsolt.util.pagination

data class ErrorItem(
    override val id: String = "errorItem",
    val errorMessage: String
): PagingItem