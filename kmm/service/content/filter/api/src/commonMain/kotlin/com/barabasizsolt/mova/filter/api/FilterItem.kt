package com.barabasizsolt.mova.filter.api

data class FilterItem(
    val name: String,
    val value: FilterItemValue,
    val wrappedItem: Any? = null
)

sealed class FilterItemValue {
    data class WithValue(val value: String) : FilterItemValue()
    object WithoutValue : FilterItemValue()
}

fun String.toFilterItemWithValue() = FilterItemValue.WithValue(value = this)
