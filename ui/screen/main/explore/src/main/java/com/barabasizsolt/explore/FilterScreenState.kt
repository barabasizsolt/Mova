package com.barabasizsolt.explore

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.barabasizsolt.domain.usecase.helper.genre.GetGenresFlowUseCase
import com.barabasizsolt.domain.usecase.screen.explore.Category
import com.barabasizsolt.domain.util.FilterType
import com.barabasizsolt.genre.api.GenreType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import java.util.Locale

@Composable
fun rememberFilterScreenState(
    getGenresFlowUseCase: GetGenresFlowUseCase = get(),
    scope: CoroutineScope = rememberCoroutineScope()
): FilterScreenState = rememberSaveable(
    saver = FilterScreenState.getSaver(
        getGenresFlowUseCase = getGenresFlowUseCase,
        scope = scope
    )
) {
    FilterScreenState(
        getGenresFlowUseCase = getGenresFlowUseCase,
        scope = scope
    )
}

class FilterScreenState(
    private val getGenresFlowUseCase: GetGenresFlowUseCase,
    private val scope: CoroutineScope
) {
    private var job: Job? = null

    val categories: List<FilterItem> = listOf(
        FilterItem(name = "All Categories", value = Category.ALL_CATEGORY.value, wrappedItem = Category.ALL_CATEGORY),
        FilterItem(name = "Movie", value = Category.MOVIE.value, wrappedItem = Category.MOVIE),
        FilterItem(name = "Tv Series", value = Category.TV.value, wrappedItem = Category.TV)
    )
    var selectedCategoryPosition by mutableStateOf(value = 0)
        private set

    val regions: List<FilterItem> = listOf(
        FilterItem(name = "All Regions", value = "")
    ) + Locale.getISOCountries().map { locale -> locale.convertToFilterItem() }
    var selectedRegionPositions by mutableStateOf(listOf(0))
        private set

    var genres by mutableStateOf<List<FilterItem>>(value = emptyList())
        private set
    var selectedGenrePositions by mutableStateOf(listOf(0))
        private set

    val sortOptions: List<FilterItem> = listOf(
        FilterItem(name = "Popularity [Default]", value = FilterType.DEFAULT.value),
        FilterItem(name = "Latest Release", value = FilterType.LATEST_RELEASE.value),
        FilterItem(name = "Vote Average", value = FilterType.VOTE_AVERAGE.value)
    )
    var selectedSortOptionPositions by mutableStateOf(listOf(0))
        private set

    init {
        restartGenresCollecting()
    }

    private fun restartGenresCollecting() {
        job?.cancel()
        job = scope.launch {
            getGenresFlowUseCase(
                genreType = when (categories[selectedCategoryPosition].wrappedItem as Category) {
                    Category.ALL_CATEGORY -> GenreType.JOINT
                    Category.MOVIE -> GenreType.MOVIE
                    Category.TV -> GenreType.TV
                }
            ).cancellable().collect {
                genres = buildList {
                    add(FilterItem(name = "All Genres", value = ""))
                    addAll(it.entries.map { FilterItem(name = it.value, value = it.key.toString()) })
                }
            }
        }
    }

    fun onCategorySelected(position: Int) {
        restartGenresCollecting()
        selectedGenrePositions = listOf(0) /*TODO: scroll to the start of the carousel*/
        selectedCategoryPosition = position
    }

    fun onRegionSelected(positions: List<Int>) {
        selectedRegionPositions = positions
    }

    fun onGenreSelected(positions: List<Int>) {
        selectedGenrePositions = positions
    }

    fun onSortingCriteriaSelected(positions: List<Int>) {
        selectedSortOptionPositions = positions
    }

    fun onResetButtonClicked() {
        selectedCategoryPosition = 0
        selectedRegionPositions = listOf(0)
        selectedGenrePositions = listOf(0)
        selectedSortOptionPositions = listOf(0)
    }

    fun onApplyButtonClicked() { }

    private fun String.convertToFilterItem(): FilterItem {
        val locale = Locale("", this)
        return FilterItem(name = locale.displayName, value = locale.country)
    }

    companion object {

        private const val CATEGORY_KEY: String = "com.barabasizsolt.explore.filterscreenstate.category"
        private const val REGION_KEY: String = "com.barabasizsolt.explore.filterscreenstate.region"
        private const val GENRE_KEY: String = "com.barabasizsolt.explore.filterscreenstate.genre"
        private const val SORT_KEY: String = "com.barabasizsolt.explore.filterscreenstate.sort"

        fun getSaver(
            getGenresFlowUseCase: GetGenresFlowUseCase,
            scope: CoroutineScope
        ): Saver<FilterScreenState, *> = mapSaver(
            save = {
                mapOf(
                    CATEGORY_KEY to it.selectedCategoryPosition,
                    REGION_KEY to it.selectedRegionPositions,
                    GENRE_KEY to it.selectedGenrePositions,
                    SORT_KEY to it.selectedSortOptionPositions
                )
            },
            restore = {
                FilterScreenState(
                    getGenresFlowUseCase = getGenresFlowUseCase,
                    scope = scope
                ).apply {
                    selectedCategoryPosition = it[CATEGORY_KEY] as Int
                    selectedRegionPositions = it[REGION_KEY] as List<Int>
                    selectedGenrePositions = it[GENRE_KEY] as List<Int>
                    selectedSortOptionPositions = it[SORT_KEY] as List<Int>
                }
            }
        )
    }
}