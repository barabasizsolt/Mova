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
import com.barabasizsolt.domain.usecase.helper.discover.movie.DeleteMovieDiscoverUseCase
import com.barabasizsolt.domain.usecase.helper.discover.tv.DeleteTvDiscoverUseCase
import com.barabasizsolt.domain.usecase.screen.explore.Category
import com.barabasizsolt.domain.usecase.screen.explore.GetExploreScreenFlowUseCase
import com.barabasizsolt.domain.usecase.screen.explore.GetExploreScreenUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import com.barabasizsolt.domain.util.result.Result
import com.barabasizsolt.util.RefreshType
import kotlinx.coroutines.delay
import org.koin.androidx.compose.get

@Composable
fun rememberExploreScreenState(
    getExploreScreen: GetExploreScreenUseCase = get(),
    getExploreScreenFlow: GetExploreScreenFlowUseCase = get(),
    deleteMovieDiscoverUseCase: DeleteMovieDiscoverUseCase = get(),
    deleteTvDiscoverUseCase: DeleteTvDiscoverUseCase = get()
): ExploreScreenState = rememberSaveable(
    saver = ExploreScreenState.getSaver(
        getExploreScreen = getExploreScreen,
        getExploreScreenFlow = getExploreScreenFlow,
        deleteMovieDiscoverUseCase = deleteMovieDiscoverUseCase,
        deleteTvDiscoverUseCase = deleteTvDiscoverUseCase
    )
) {
    ExploreScreenState(
        getExploreScreen = getExploreScreen,
        getExploreScreenFlow = getExploreScreenFlow,
        deleteMovieDiscoverUseCase = deleteMovieDiscoverUseCase,
        deleteTvDiscoverUseCase = deleteTvDiscoverUseCase
    )
}

class ExploreScreenState(
    private val getExploreScreen: GetExploreScreenUseCase,
    private val getExploreScreenFlow: GetExploreScreenFlowUseCase,
    private val deleteMovieDiscoverUseCase: DeleteMovieDiscoverUseCase,
    private val deleteTvDiscoverUseCase: DeleteTvDiscoverUseCase
) : BaseScreenState() {
    var exploreContent by mutableStateOf<List<ContentItem>>(value = emptyList())
        private set
    var query by mutableStateOf(value = "")
        private set
    private var category: Category = Category.MOVIE

    init {
        getExploreScreenFlow(category = category).onEach {
            println("<<Size: ${it.size}")
            exploreContent = it
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
            }
            scope.launch {
                delay(timeMillis = delay)
                state = when (
                    val result = getExploreScreen(
                        query = query,
                        refreshType = when {
                            userAction is UserAction.SwipeRefresh -> RefreshType.FORCE_REFRESH
                            exploreContent.isEmpty() -> RefreshType.CACHE_IF_POSSIBLE
                            else -> RefreshType.NEXT_PAGE
                        }
                    )
                ) {
                    is Result.Failure -> when {
                        userAction is UserAction.Normal && exploreContent.isEmpty() ->
                            State.Error(message = result.exception.message.orEmpty())
                        userAction is UserAction.SwipeRefresh ->
                            State.ShowSnackBar
                        userAction is UserAction.Search ->
                            State.Normal.also {
                                //exploreContent = listOf(ContentItem.ItemError())
                            }
                        userAction is UserAction.Normal ->
                            State.Normal.also {
                                //exploreContent = exploreContent.take(n = exploreContent.size - 1) + listOf(ContentItem.ItemError())
                            }
                        else -> State.Error(message = result.exception.message.orEmpty())
                    }
                    is Result.Success -> State.Normal.also {
                        if (exploreContent.isNotEmpty()) {
                            println("<<LastItem: ${exploreContent[exploreContent.lastIndex]}")
                        }
                    }
                }
            }
        }
    }

    fun onQueryChange(query: String) {
        this.query = query
        //TODO [MID] move into a useCase
        if (category == Category.MOVIE) deleteMovieDiscoverUseCase() else deleteTvDiscoverUseCase()
        getScreenData(userAction = UserAction.Search, delay = 500)
    }

    companion object {
        private const val QUERY_KEY: String = "query"

        fun getSaver(
            getExploreScreen: GetExploreScreenUseCase,
            getExploreScreenFlow: GetExploreScreenFlowUseCase,
            deleteMovieDiscoverUseCase: DeleteMovieDiscoverUseCase,
            deleteTvDiscoverUseCase: DeleteTvDiscoverUseCase
        ): Saver<ExploreScreenState, *> = getBaseSaver(
            save = { mapOf(QUERY_KEY to it.query) },
            restore = {
                ExploreScreenState(
                    getExploreScreen = getExploreScreen,
                    getExploreScreenFlow = getExploreScreenFlow,
                    deleteMovieDiscoverUseCase = deleteMovieDiscoverUseCase,
                    deleteTvDiscoverUseCase = deleteTvDiscoverUseCase
                ).apply {
                    query = it[QUERY_KEY] as String
                }
            }
        )
    }
}