package com.barabasizsolt.explore

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.barabasizsolt.base.BaseScreenState
import com.barabasizsolt.base.UserAction
import com.barabasizsolt.domain.model.ContentItem
import com.barabasizsolt.domain.usecase.screen.explore.discover.DiscoverContentFlowUseCase
import com.barabasizsolt.domain.usecase.screen.explore.discover.DiscoverContentUseCase
import com.barabasizsolt.domain.usecase.screen.explore.search.DeleteContentUseCase
import com.barabasizsolt.domain.usecase.screen.explore.search.SearchContentFlowUseCase
import com.barabasizsolt.domain.usecase.screen.explore.search.SearchContentUseCase
import kotlinx.coroutines.launch
import com.barabasizsolt.domain.util.result.Result
import com.barabasizsolt.filter.api.Category
import com.barabasizsolt.filter.api.FilterItem
import com.barabasizsolt.filter.api.FilterService
import com.barabasizsolt.pagination.api.RefreshType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.get

@Composable
fun rememberExploreScreenState(
    discoverContentUseCase: DiscoverContentUseCase = get(),
    discoverContentFlowUseCase: DiscoverContentFlowUseCase = get(),
    searchContentUseCase: SearchContentUseCase = get(),
    searchContentFlowUseCase: SearchContentFlowUseCase = get(),
    deleteContentUseCase: DeleteContentUseCase = get(),
    filterService: FilterService = get()
): ExploreScreenState = rememberSaveable(
    saver = ExploreScreenState.getSaver(
        discoverContentUseCase = discoverContentUseCase,
        discoverContentFlowUseCase = discoverContentFlowUseCase,
        searchContentUseCase = searchContentUseCase,
        searchContentFlowUseCase = searchContentFlowUseCase,
        deleteContentUseCase = deleteContentUseCase,
        filterService = filterService
    )
) {
    ExploreScreenState(
        discoverContentUseCase = discoverContentUseCase,
        discoverContentFlowUseCase = discoverContentFlowUseCase,
        searchContentUseCase = searchContentUseCase,
        searchContentFlowUseCase = searchContentFlowUseCase,
        deleteContentUseCase = deleteContentUseCase,
        filterService = filterService
    )
}

class ExploreScreenState(
    private val discoverContentUseCase: DiscoverContentUseCase,
    private val discoverContentFlowUseCase: DiscoverContentFlowUseCase,
    private val searchContentUseCase: SearchContentUseCase,
    private  val searchContentFlowUseCase: SearchContentFlowUseCase,
    private val deleteContentUseCase: DeleteContentUseCase,
    private val filterService: FilterService
) : BaseScreenState() {
    private var discoverJob: Job? = null
    private var searchJob: Job? = null

    var discoverContent by mutableStateOf<List<ContentItem>>(value = emptyList())
        private set
    var searchContent by mutableStateOf<List<ContentItem>>(value = emptyList())
        private set
    var query by mutableStateOf(value = "")
        private set

    var selectedCategory by mutableStateOf(value = filterService.categories[0])
        private set
    private var selectedRegions by mutableStateOf<List<FilterItem>>(value = emptyList())
    private var selectedGenres by mutableStateOf<List<FilterItem>>(value = emptyList())
    private var selectedSortOptions by mutableStateOf<List<FilterItem>>(value = emptyList())

    init {

        filterService.selectedCategory.observe {
            /*TODO: Improve It*/
            selectedCategory = it
            restartDiscoverContentCollection()
            restartSearchContentCollection()
            query = ""
            getScreenData(userAction = UserAction.Normal)
        }
        filterService.selectedRegions.observe { selectedRegions = it }
        filterService.selectedGenres.observe { selectedGenres = it }
        filterService.selectedSortOptions.observe { selectedSortOptions = it }

        restartDiscoverContentCollection()
        restartSearchContentCollection()

        getScreenData(userAction = UserAction.Normal)
    }

    override fun getScreenData(userAction: UserAction, delay: Long) {
        if (state !in listOf(State.Loading, State.SwipeRefresh, State.Search)) {
            state = when (userAction) {
                UserAction.SwipeRefresh -> State.SwipeRefresh
                UserAction.Search -> State.Search
                UserAction.Error -> State.Loading
                UserAction.Normal -> State.Normal
                UserAction.TryAgain -> State.TryAgainLoading
            }
            scope.launch {
                delay(timeMillis = delay)
                if (query.isEmpty()) {
                    discoverContent(userAction = userAction)
                } else {
                    searchContent(userAction = userAction, query = query)
                }
            }
        }
    }

    private fun restartDiscoverContentCollection() {
        discoverJob?.cancel()
        discoverJob = scope.launch {
            discoverContentFlowUseCase(category = selectedCategory.wrappedItem as Category).cancellable().collect {
                discoverContent = it
            }
        }
    }

    private fun restartSearchContentCollection() {
        searchJob?.cancel()
        searchJob = scope.launch {
            searchContentFlowUseCase(category = selectedCategory.wrappedItem as Category).cancellable().collect {
                searchContent = it
            }
        }
    }

    fun onQueryChange(query: String) {
        this.query = query
        clearSearchContent()
        getScreenData(userAction = UserAction.Search, delay = 500)
    }

    fun clearSearchContent() = deleteContentUseCase(category = selectedCategory.wrappedItem as Category)

    private suspend fun discoverContent(userAction: UserAction) {
        state = when (
            val result = discoverContentUseCase(
                refreshType = when {
                    userAction is UserAction.SwipeRefresh -> RefreshType.FORCE_REFRESH
                    discoverContent.isEmpty() -> RefreshType.CACHE_IF_POSSIBLE
                    else -> RefreshType.NEXT_PAGE
                },
                category = selectedCategory.wrappedItem as Category
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
        userAction is UserAction.Normal && (if (isSearch) searchContent.size <= 1 else discoverContent.size <= 1) ->
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

    companion object {
        private const val QUERY_KEY: String = "com.barabasizsolt.explore.query"

        fun getSaver(
            discoverContentUseCase: DiscoverContentUseCase,
            discoverContentFlowUseCase: DiscoverContentFlowUseCase,
            searchContentUseCase: SearchContentUseCase,
            searchContentFlowUseCase: SearchContentFlowUseCase,
            deleteContentUseCase: DeleteContentUseCase,
            filterService: FilterService
        ): Saver<ExploreScreenState, *> = getBaseSaver(
            save = { mapOf(QUERY_KEY to it.query) },
            restore = {
                ExploreScreenState(
                    discoverContentUseCase = discoverContentUseCase,
                    discoverContentFlowUseCase = discoverContentFlowUseCase,
                    searchContentUseCase = searchContentUseCase,
                    searchContentFlowUseCase = searchContentFlowUseCase,
                    deleteContentUseCase = deleteContentUseCase,
                    filterService = filterService
                ).apply {
                    query = it[QUERY_KEY] as String
                }
            }
        )
    }
}