package com.barabasizsolt.mova.filter.implementation

import com.barabasizsolt.mova.filter.api.FilterItem
import com.barabasizsolt.mova.filter.api.toFilterItemWithValue
import java.util.Locale

actual fun getRegions(): List<FilterItem> = Locale.getISOCountries().map {
    it.convertISOCountryToFilterItem()
}

private fun String.convertISOCountryToFilterItem(): FilterItem {
    val locale = Locale("", this)
    return FilterItem(
        name = locale.displayName,
        value = locale.country.toFilterItemWithValue()
    )
}