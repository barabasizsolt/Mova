package com.barabasizsolt.filter.implementation

import com.barabasizsolt.filter.api.Category
import com.barabasizsolt.filter.api.FilterItem
import com.barabasizsolt.filter.api.FilterItemValue
import com.barabasizsolt.filter.api.FilterService
import com.barabasizsolt.filter.api.SortOption
import com.barabasizsolt.filter.api.firstItemToList
import com.barabasizsolt.filter.api.toFilterItemWithValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Locale

class FilterServiceImpl : FilterService {
    override val categories: List<FilterItem>
        get() = listOf(
            FilterItem(name = "Movie", value = Category.MOVIE.value.toFilterItemWithValue(), wrappedItem = Category.MOVIE),
            FilterItem(name = "Tv Series", value = Category.TV.value.toFilterItemWithValue(), wrappedItem = Category.TV)
        )

    private val _selectedCategory = MutableStateFlow(value = categories[0])
    override val selectedCategory: Flow<FilterItem> = _selectedCategory

    override val regions: List<FilterItem>
        get() = listOf(
            FilterItem(name = "All Regions", value = FilterItemValue.WithoutValue)
        ) + Locale.getISOCountries().map { locale -> locale.convertToFilterItem() }

    private val _selectedRegions = MutableStateFlow(value = regions.firstItemToList())
    override val selectedRegions: Flow<List<FilterItem>> = _selectedRegions

    private val defaultGenres = listOf(FilterItem(name = "All Genres", value = FilterItemValue.WithoutValue))
    private val _selectedGenres = MutableStateFlow(value = defaultGenres)
    override val selectedGenres: Flow<List<FilterItem>> = _selectedGenres

    override val sortOptions: List<FilterItem>
        get() = listOf(
            FilterItem(name = "Popularity [Default]", value = SortOption.DEFAULT.value.toFilterItemWithValue()),
            FilterItem(name = "Latest Release", value = SortOption.LATEST_RELEASE.value.toFilterItemWithValue()),
            FilterItem(name = "Vote Average", value = SortOption.VOTE_AVERAGE.value.toFilterItemWithValue())
        )

    private val _selectedSortOptions = MutableStateFlow(value = sortOptions.firstItemToList())
    override val selectedSortOptions: Flow<List<FilterItem>> = _selectedSortOptions

    override fun onCategoryChange(selectedCategory: FilterItem) {
        _selectedCategory.value = selectedCategory
        _selectedGenres.value = defaultGenres
    }

    override fun onRegionsChange(selectedRegions: List<FilterItem>) {
        _selectedRegions.value = selectedRegions
    }

    override fun onGenresChange(selectedGenres: List<FilterItem>) {
        _selectedGenres.value = selectedGenres
    }

    override fun onSortOptionChange(selectedSortOptions: List<FilterItem>) {
        _selectedSortOptions.value = selectedSortOptions
    }

    private fun String.convertToFilterItem(): FilterItem {
        val locale = Locale("", this)
        return FilterItem(name = locale.displayName, value = locale.country.toFilterItemWithValue())
    }
}