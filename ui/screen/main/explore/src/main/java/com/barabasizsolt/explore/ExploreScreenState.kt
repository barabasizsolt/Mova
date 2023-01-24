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
import com.barabasizsolt.domain.usecase.screen.explore.Category
import com.barabasizsolt.domain.usecase.screen.explore.discover.DiscoverContentFlowUseCase
import com.barabasizsolt.domain.usecase.screen.explore.discover.DiscoverContentUseCase
import com.barabasizsolt.domain.usecase.screen.explore.search.DeleteContentUseCase
import com.barabasizsolt.domain.usecase.screen.explore.search.SearchContentFlowUseCase
import com.barabasizsolt.domain.usecase.screen.explore.search.SearchContentUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import com.barabasizsolt.domain.util.result.Result
import com.barabasizsolt.pagination.api.RefreshType
import kotlinx.coroutines.delay
import org.koin.androidx.compose.get

@Composable
fun rememberExploreScreenState(
    discoverContentUseCase: DiscoverContentUseCase = get(),
    discoverContentFlowUseCase: DiscoverContentFlowUseCase = get(),
    searchContentUseCase: SearchContentUseCase = get(),
    searchContentFlowUseCase: SearchContentFlowUseCase = get(),
    deleteContentUseCase: DeleteContentUseCase = get()
): ExploreScreenState = rememberSaveable(
    saver = ExploreScreenState.getSaver(
        discoverContentUseCase = discoverContentUseCase,
        discoverContentFlowUseCase = discoverContentFlowUseCase,
        searchContentUseCase = searchContentUseCase,
        searchContentFlowUseCase = searchContentFlowUseCase,
        deleteContentUseCase = deleteContentUseCase
    )
) {
    ExploreScreenState(
        discoverContentUseCase = discoverContentUseCase,
        discoverContentFlowUseCase = discoverContentFlowUseCase,
        searchContentUseCase = searchContentUseCase,
        searchContentFlowUseCase = searchContentFlowUseCase,
        deleteContentUseCase = deleteContentUseCase
    )
}

class ExploreScreenState(
    private val discoverContentUseCase: DiscoverContentUseCase,
    private val discoverContentFlowUseCase: DiscoverContentFlowUseCase,
    private val searchContentUseCase: SearchContentUseCase,
    private  val searchContentFlowUseCase: SearchContentFlowUseCase,
    private val deleteContentUseCase: DeleteContentUseCase
) : BaseScreenState() {
    var discoverContent by mutableStateOf<List<ContentItem>>(value = emptyList())
        private set
    var searchContent by mutableStateOf<List<ContentItem>>(value = emptyList())
        private set
    var query by mutableStateOf(value = "")
        private set
    var category by mutableStateOf(value = Category.MOVIE)
        private set

    init {
        discoverContentFlowUseCase(category = category).onEach {
            discoverContent = it
        }.launchIn(scope = scope)

        searchContentFlowUseCase(category = category).onEach {
            searchContent = it
        }.launchIn(scope = scope)

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

    fun onQueryChange(query: String) {
        this.query = query
        clearSearchContent()
        getScreenData(userAction = UserAction.Search, delay = 500)
    }

    fun clearSearchContent() = deleteContentUseCase(category = category)

    private suspend fun discoverContent(userAction: UserAction) {
        state = when (
            val result = discoverContentUseCase(
                refreshType = when {
                    userAction is UserAction.SwipeRefresh -> RefreshType.FORCE_REFRESH
                    discoverContent.isEmpty() -> RefreshType.CACHE_IF_POSSIBLE
                    else -> RefreshType.NEXT_PAGE
                },
                category = category
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
                category = category,
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
        private const val QUERY_KEY: String = "query"

        fun getSaver(
            discoverContentUseCase: DiscoverContentUseCase,
            discoverContentFlowUseCase: DiscoverContentFlowUseCase,
            searchContentUseCase: SearchContentUseCase,
            searchContentFlowUseCase: SearchContentFlowUseCase,
            deleteContentUseCase: DeleteContentUseCase
        ): Saver<ExploreScreenState, *> = getBaseSaver(
            save = { mapOf(QUERY_KEY to it.query) },
            restore = {
                ExploreScreenState(
                    discoverContentUseCase = discoverContentUseCase,
                    discoverContentFlowUseCase = discoverContentFlowUseCase,
                    searchContentUseCase = searchContentUseCase,
                    searchContentFlowUseCase = searchContentFlowUseCase,
                    deleteContentUseCase = deleteContentUseCase
                ).apply {
                    query = it[QUERY_KEY] as String
                }
            }
        )
    }
}