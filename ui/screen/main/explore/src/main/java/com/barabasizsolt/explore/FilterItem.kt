package com.barabasizsolt.explore

data class FilterItem(
    val name: String,
    val value: String,
    val wrappedItem: Any? = null
)