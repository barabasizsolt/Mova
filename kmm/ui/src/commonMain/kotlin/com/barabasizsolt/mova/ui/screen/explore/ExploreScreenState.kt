package com.barabasizsolt.mova.ui.screen.explore

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import category.Category
import com.barabasizsolt.mova.domain.model.ContentItem
import com.barabasizsolt.mova.domain.usecase.helper.genre.GetGenresUseCase
import com.barabasizsolt.mova.domain.usecase.screen.explore.discover.DiscoverContentFlowUseCase
import com.barabasizsolt.mova.domain.usecase.screen.explore.discover.DiscoverContentUseCase
import com.barabasizsolt.mova.domain.usecase.screen.explore.search.DeleteContentUseCase
import com.barabasizsolt.mova.domain.usecase.screen.explore.search.SearchContentFlowUseCase
import com.barabasizsolt.mova.domain.usecase.screen.explore.search.SearchContentUseCase
import com.barabasizsolt.mova.domain.util.Result
import com.barabasizsolt.mova.filter.api.FilterItem
import com.barabasizsolt.mova.filter.api.FilterItemValue
import com.barabasizsolt.mova.filter.api.FilterService
import com.barabasizsolt.mova.pager.RefreshType
import com.barabasizsolt.mova.ui.screen.base.BaseScreenState
import com.barabasizsolt.mova.ui.screen.base.UserAction
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch

class ExploreScreenState(
    private val discoverContentUseCase: DiscoverContentUseCase,
    private val discoverContentFlowUseCase: DiscoverContentFlowUseCase,
    private val searchContentUseCase: SearchContentUseCase,
    private  val searchContentFlowUseCase: SearchContentFlowUseCase,
    private val deleteContentUseCase: DeleteContentUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val filterService: FilterService
) : BaseScreenState(), ScreenModel {
    private var discoverJob: Job? = null
    private var searchJob: Job? = null

    var discoverContent by mutableStateOf<List<ContentItem>>(value = emptyList())
        private set
    var searchContent by mutableStateOf<List<ContentItem>>(value = emptyList())
        private set

    var selectedCategory by mutableStateOf(value = filterService.categories[0])
        private set
    var selectedRegions by mutableStateOf<List<FilterItem>>(value = emptyList())
        private set
    var selectedGenres by mutableStateOf<List<FilterItem>>(value = emptyList())
        private set
    var selectedSortOptions by mutableStateOf<List<FilterItem>>(value = emptyList())
        private set

    private var movieQuery by mutableStateOf(value = "")
    private var tvQuery by mutableStateOf(value = "")
    val query by derivedStateOf {
        when (selectedCategory.wrappedItem as Category) {
            Category.MOVIE -> movieQuery
            Category.TV -> tvQuery
        }
    }
    private var isInitialized: Boolean = false

    init {
        coroutineScope.launch {
            launch {
                filterService.selectedCategory.collect {
                    selectedCategory = it
                    /*TODO: Keep this order*/
                    restartSearchContentCollection()
                    restartDiscoverContentCollection()
                    getScreenData(userAction = if (isInitialized) UserAction.Normal else UserAction.Search.also { isInitialized = true })
                }
            }
            launch { filterService.selectedRegions.collect { selectedRegions = it } }
            launch { filterService.selectedGenres.collect { selectedGenres = it } }
            launch { filterService.selectedSortOptions.collect { selectedSortOptions = it } }
        }
    }

    override fun getScreenData(userAction: UserAction, delay: Long) {
        if (state !in listOf(State.Loading, State.SwipeRefresh, State.SearchLoading)) {
            state = when (userAction) {
                UserAction.SwipeRefresh -> State.SwipeRefresh
                UserAction.Search -> State.SearchLoading
                UserAction.Error -> State.Loading
                UserAction.Normal -> State.Normal
                UserAction.TryAgain -> State.TryAgainLoading
            }
            coroutineScope.launch {
                delay(timeMillis = delay)
                if (query.isEmpty()) {
                    discoverContent(userAction = userAction)
                } else {
                    searchContent(userAction = userAction, query = query)
                }
            }
        }

        coroutineScope.launch {
            when(getGenresUseCase()) {
                is Result.Failure, is Result.Success -> Unit
            }
        }
    }

    private fun restartDiscoverContentCollection() {
        discoverJob?.cancel()
        discoverJob = coroutineScope.launch {
            discoverContentFlowUseCase(category = selectedCategory.wrappedItem as Category).cancellable().collect {
                discoverContent = it
            }
        }
    }

    private fun restartSearchContentCollection() {
        searchJob?.cancel()
        searchJob = coroutineScope.launch {
            searchContentFlowUseCase(category = selectedCategory.wrappedItem as Category).cancellable().collect {
                searchContent = it
            }
        }
    }

    fun onQueryChange(query: String) {
        when (selectedCategory.wrappedItem as Category) {
            Category.MOVIE -> movieQuery = query
            Category.TV -> tvQuery = query
        }
        clearSearchContent()
        getScreenData(userAction = UserAction.Search, delay = 500)
    }

    fun clearSearchContent() = deleteContentUseCase(category = selectedCategory.wrappedItem as Category)

    fun onApplyButtonClicked() {
        restartDiscoverContentCollection()
        restartSearchContentCollection()
        if (query.isEmpty()) {
            state = State.SearchLoading
        }
        coroutineScope.launch {
            delay(timeMillis = 100L)
            discoverContent(userAction = UserAction.SwipeRefresh)
            state = State.Normal
        }
    }

    fun onResetButtonClicked() {
        restartDiscoverContentCollection()
        restartSearchContentCollection()
        state = State.SearchLoading
        coroutineScope.launch {
            delay(timeMillis = 100L)
            when (selectedCategory.wrappedItem as Category) {
                Category.MOVIE -> movieQuery = ""
                Category.TV -> tvQuery = ""
            }
            discoverContent(userAction = UserAction.SwipeRefresh)
            searchContent(userAction = UserAction.SwipeRefresh, query = query)
            state = State.Normal
        }
    }

    private suspend fun discoverContent(userAction: UserAction) {
        state = when (
            val result = discoverContentUseCase(
                refreshType = when {
                    userAction is UserAction.SwipeRefresh -> RefreshType.FORCE_REFRESH
                    discoverContent.isEmpty() -> RefreshType.CACHE_IF_POSSIBLE
                    else -> RefreshType.NEXT_PAGE
                },
                category = selectedCategory.wrappedItem as Category,
                region = if (selectedRegions.size == 1 && selectedRegions[0].value is FilterItemValue.WithoutValue) {
                    emptyList()
                } else {
                    selectedRegions.map { (it.value as FilterItemValue.WithValue).value }
                },
                withGenres = if (selectedGenres.size == 1 && selectedGenres[0].value is FilterItemValue.WithoutValue) {
                    emptyList()
                } else {
                    selectedGenres.map { (it.value as FilterItemValue.WithValue).value.toInt() }
                },
                sortBy = selectedSortOptions.map { (it.value as FilterItemValue.WithValue).value }
            )
        ) {
            is Result.Failure -> handleError(userAction = userAction, errorMessage = result.exception.message.orEmpty())
            is Result.Success -> State.Normal
        }
    }

    private suspend fun searchContent(userAction: UserAction, query: String) {
        state = when (
            val result = searchContentUseCase(
                refreshType = when (userAction) {
                    is UserAction.SwipeRefresh -> RefreshType.FORCE_REFRESH
                    is UserAction.Search, is UserAction.Error -> RefreshType.CACHE_IF_POSSIBLE
                    is UserAction.Normal -> RefreshType.NEXT_PAGE
                    is UserAction.TryAgain -> if (searchContent.size <= 1) RefreshType.CACHE_IF_POSSIBLE else RefreshType.NEXT_PAGE
                },
                category = selectedCategory.wrappedItem as Category,
                query = query
            )
        ) {
            is Result.Failure -> handleError(userAction = userAction, errorMessage = result.exception.message.orEmpty(), isSearch = true)
            is Result.Success -> State.Normal
        }
    }

    private fun handleError(userAction: UserAction, errorMessage: String, isSearch: Boolean = false) = when {
        userAction is UserAction.Normal && (if (isSearch) searchContent.size <= 1 else discoverContent.size <= 1) && query.isEmpty() ->
            State.Error(message = errorMessage)
        userAction is UserAction.SwipeRefresh ->
            State.ShowSnackBar
        userAction is UserAction.Search ->
            State.Normal
        userAction is UserAction.Normal ->
            State.Normal
        userAction is UserAction.TryAgain ->
            State.Normal
        else -> State.Error(message = errorMessage)
    }
}