package com.barabasizsolt.explore

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.barabasizsolt.domain.usecase.helper.genre.GetGenresFlowUseCase
import com.barabasizsolt.filter.api.Category
import com.barabasizsolt.filter.api.FilterItem
import com.barabasizsolt.filter.api.FilterItemValue
import com.barabasizsolt.filter.api.FilterService
import com.barabasizsolt.filter.api.firstItemToList
import com.barabasizsolt.filter.api.toFilterItemWithValue
import com.barabasizsolt.genre.api.GenreType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@Composable
fun rememberFilterScreenState(
    getGenresFlowUseCase: GetGenresFlowUseCase = get(),
    scope: CoroutineScope = rememberCoroutineScope(),
    filterService: FilterService = get()
): FilterScreenState = remember {
    FilterScreenState(
        getGenresFlowUseCase = getGenresFlowUseCase,
        scope = scope,
        filterService = filterService
    )
}

class FilterScreenState(
    private val getGenresFlowUseCase: GetGenresFlowUseCase,
    private val scope: CoroutineScope,
    private val filterService: FilterService
) {
    private var job: Job? = null

    val categories = filterService.categories
    var selectedCategory by mutableStateOf(value = categories[0])
        private set

    val regions = filterService.regions
    var selectedRegions by mutableStateOf<List<FilterItem>>(value = emptyList())
        private set

    var genres by mutableStateOf<List<FilterItem>>(value = emptyList())
        private set
    var selectedGenres by mutableStateOf<List<FilterItem>>(value = emptyList())
        private set

    val sortOptions = filterService.sortOptions
    var selectedSortOptions by mutableStateOf<List<FilterItem>>(value = emptyList())
        private set

    init {
        filterService.selectedCategory.observe { selectedCategory = it }
        filterService.selectedRegions.observe { selectedRegions = it }
        filterService.selectedGenres.observe { selectedGenres = it }
        filterService.selectedSortOptions.observe { selectedSortOptions = it }
        restartGenresCollecting()
    }

    private fun restartGenresCollecting() {
        job?.cancel()
        job = scope.launch {
            getGenresFlowUseCase(
                genreType = when (selectedCategory.wrappedItem as Category) {
                    Category.MOVIE -> GenreType.MOVIE
                    Category.TV -> GenreType.TV
                }
            ).cancellable().collect {
                genres = buildList {
                    add(FilterItem(name = "All Genres", value = FilterItemValue.WithoutValue))
                    addAll(it.entries.map { FilterItem(name = it.value, value = it.key.toString().toFilterItemWithValue()) })
                }
            }
        }
    }

    fun onCategorySelected(category: FilterItem) {
        restartGenresCollecting()
        filterService.onCategoryChange(selectedCategory = category)
        filterService.onGenresChange(selectedGenres = genres.firstItemToList())
    }

    fun onRegionSelected(regions: List<FilterItem>) {
        filterService.onRegionsChange(selectedRegions = regions)
    }

    fun onGenreSelected(genres: List<FilterItem>) {
        filterService.onGenresChange(selectedGenres = genres)
    }

    fun onSortingCriteriaSelected(sortOptions: List<FilterItem>) {
        filterService.onSortOptionChange(selectedSortOptions = sortOptions)
    }

    fun onResetButtonClicked() {
        filterService.onCategoryChange(selectedCategory = categories[0])
        filterService.onRegionsChange(selectedRegions = regions.firstItemToList())
        filterService.onGenresChange(selectedGenres = genres.firstItemToList())
        filterService.onSortOptionChange(selectedSortOptions = sortOptions.firstItemToList())
    }

    fun onApplyButtonClicked() { }

    private fun <T> Flow<T>.observe(action: suspend (T) -> Unit) = this.onEach(action).launchIn(scope = scope)
}