package com.barabasizsolt.filter.api

data class FilterItem(
    val name: String,
    val value: String,
    val wrappedItem: Any? = null
)
