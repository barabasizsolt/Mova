package com.barabasizsolt.filter.api

import kotlinx.coroutines.flow.Flow

interface FilterService {
    val categories: List<FilterItem>
    val selectedCategory: Flow<FilterItem>

    val regions: List<FilterItem>
    val selectedRegions: Flow<List<FilterItem>>

    val selectedGenres: Flow<List<FilterItem>>

    val sortOptions: List<FilterItem>
    val selectedSortOptions: Flow<List<FilterItem>>

    fun onCategoryChange(selectedCategory: FilterItem)

    fun onRegionsChange(selectedRegions: List<FilterItem>)

    fun onGenresChange(selectedGenres: List<FilterItem>)

    fun onSortOptionChange(selectedSortOptions: List<FilterItem>)
}