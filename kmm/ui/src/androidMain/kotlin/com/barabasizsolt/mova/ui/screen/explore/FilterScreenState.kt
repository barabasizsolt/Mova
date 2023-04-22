package com.barabasizsolt.mova.ui.screen.explore

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import category.Category
import com.barabasizsolt.mova.domain.usecase.helper.genre.GetGenresFlowUseCase
import com.barabasizsolt.mova.filter.api.FilterItem
import com.barabasizsolt.mova.filter.api.FilterItemValue
import com.barabasizsolt.mova.filter.api.FilterService
import com.barabasizsolt.mova.filter.api.firstItemToList
import com.barabasizsolt.mova.filter.api.toFilterItemWithValue
import com.barabasizsolt.mova.genre.api.GenreType
import com.barabasizsolt.mova.ui.screen.base.BaseScreenState
import com.barabasizsolt.mova.ui.screen.base.UserAction
import com.barabasizsolt.mova.ui.util.Event
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class FilterScreenState(
    private val getGenresFlowUseCase: GetGenresFlowUseCase,
    private val filterService: FilterService
) : BaseScreenState(), ScreenModel {
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

    var action by mutableStateOf<Event<Action>?>(value = null)
        private set

    init {
        filterService.selectedCategory.onEach { selectedCategory = it }.launchIn(scope = coroutineScope)
        filterService.selectedRegions.onEach { selectedRegions = it }.launchIn(scope = coroutineScope)
        filterService.selectedGenres.onEach { selectedGenres = it }.launchIn(scope = coroutineScope)
        filterService.selectedSortOptions.onEach { selectedSortOptions = it }.launchIn(scope = coroutineScope)
        getScreenData(userAction = UserAction.Normal)
    }

    override fun getScreenData(userAction: UserAction, delay: Long) {
        restartGenresCollection()
    }

    private fun restartGenresCollection() {
        job?.cancel()
        job = coroutineScope.launch {
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
        filterService.onCategoryChange(selectedCategory = category)
        restartGenresCollection()
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
        filterService.onRegionsChange(selectedRegions = regions.firstItemToList())
        filterService.onGenresChange(selectedGenres = genres.firstItemToList())
        filterService.onSortOptionChange(selectedSortOptions = sortOptions.firstItemToList())
        action = Event(data = Action.OnResetButtonClicked)
    }

    fun onApplyButtonClicked() {
        action = Event(data = Action.OnApplyButtonClicked)
    }

    sealed class Action {
        object OnApplyButtonClicked : Action()
        object OnResetButtonClicked : Action()
    }
}