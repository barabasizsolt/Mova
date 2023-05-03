package com.barabasizsolt.mova.filter.implementation

import category.Category
import com.barabasizsolt.mova.filter.api.FilterItem
import com.barabasizsolt.mova.filter.api.FilterItemValue
import com.barabasizsolt.mova.filter.api.FilterService
import com.barabasizsolt.mova.filter.api.SortOption
import com.barabasizsolt.mova.filter.api.firstItemToList
import com.barabasizsolt.mova.filter.api.toFilterItemWithValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

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
        ) + getRegions()

    private val _selectedRegions = MutableStateFlow(value = regions.firstItemToList())
    override val selectedRegions: Flow<List<FilterItem>> = combine(_selectedCategory, _selectedRegions) { _, regions -> regions }

    private val defaultGenres = listOf(FilterItem(name = "All Genres", value = FilterItemValue.WithoutValue))
    private val _selectedMovieGenres = MutableStateFlow(value = defaultGenres)
    private val _selectedTvGenres = MutableStateFlow(value = defaultGenres)
    override val selectedGenres: Flow<List<FilterItem>> = combine(_selectedCategory, _selectedMovieGenres, _selectedTvGenres) { category, movie, tv ->
        when (category.wrappedItem as Category) {
            Category.MOVIE -> movie
            Category.TV -> tv
        }
    }

    override val sortOptions: List<FilterItem>
        get() = listOf(
            FilterItem(name = "Popularity [Default]", value = SortOption.DEFAULT.value.toFilterItemWithValue()),
            FilterItem(name = "Latest Release", value = SortOption.LATEST_RELEASE.value.toFilterItemWithValue()),
            FilterItem(name = "Vote Average", value = SortOption.VOTE_AVERAGE.value.toFilterItemWithValue())
        )

    private val _selectedMovieSortOptions = MutableStateFlow(value = sortOptions.firstItemToList())
    private val _selectedTvSortOptions = MutableStateFlow(value = sortOptions.firstItemToList())
    override val selectedSortOptions: Flow<List<FilterItem>> = combine(_selectedCategory, _selectedMovieSortOptions, _selectedTvSortOptions) { category, movie, tv ->
        when (category.wrappedItem as Category) {
            Category.MOVIE -> movie
            Category.TV -> tv
        }
    }

    override fun onCategoryChange(selectedCategory: FilterItem) {
        _selectedCategory.value = selectedCategory
    }

    override fun onRegionsChange(selectedRegions: List<FilterItem>) {
        _selectedRegions.value = selectedRegions
    }

    override fun onGenresChange(selectedGenres: List<FilterItem>) {
        when (_selectedCategory.value.wrappedItem as Category) {
            Category.MOVIE -> _selectedMovieGenres.value = selectedGenres
            Category.TV -> _selectedTvGenres.value = selectedGenres
        }
    }

    override fun onSortOptionChange(selectedSortOptions: List<FilterItem>) {
        when (_selectedCategory.value.wrappedItem as Category) {
            Category.MOVIE -> _selectedMovieSortOptions.value = selectedSortOptions
            Category.TV -> _selectedTvSortOptions.value = selectedSortOptions
        }
    }
}