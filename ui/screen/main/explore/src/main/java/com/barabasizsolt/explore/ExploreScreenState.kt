package com.barabasizsolt.explore

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.barabasizsolt.base.BaseScreenState
import com.barabasizsolt.domain.model.WatchableItem
import com.barabasizsolt.domain.usecase.helper.discover.movie.DeleteMovieDiscoverUseCase
import com.barabasizsolt.domain.usecase.helper.discover.tv.DeleteTvDiscoverUseCase
import com.barabasizsolt.domain.usecase.screen.explore.Category
import com.barabasizsolt.domain.usecase.screen.explore.GetExploreScreenFlowUseCase
import com.barabasizsolt.domain.usecase.screen.explore.GetExploreScreenUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import com.barabasizsolt.domain.util.Result
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
    var exploreContent by mutableStateOf<List<WatchableItem>>(value = emptyList())
        private set
    var query by mutableStateOf(value = "")
        private set
    private var category: Category = Category.MOVIE

    init {
        getExploreScreenFlow(category = category).onEach {
            exploreContent = it
        }.launchIn(scope = scope)
        getScreenData(isUserAction = false)
    }

    override fun getScreenData(isUserAction: Boolean, delay: Long) {
        if (state !in listOf(State.Loading, State.UserAction)) {
            state = if (isUserAction) State.UserAction else State.Normal
            scope.launch {
                delay(timeMillis = delay)
                state = when (
                    val result = getExploreScreen(
                        query = query,
                        refreshType = when {
                            //TODO: [MID] add swipe refresh
                            //isUserAction -> RefreshType.FORCE_REFRESH
                            exploreContent.isEmpty() -> RefreshType.CACHE_IF_POSSIBLE
                            else -> RefreshType.NEXT_PAGE
                        }
                    )
                ) {
                    is Result.Failure -> {
                        if (!isUserAction) State.Error(message = result.exception.message.orEmpty()) else State.ShowSnackBar
                    }
                    is Result.Success -> {
                        State.Normal
                    }
                }
            }
        }
    }

    fun onQueryChange(query: String) {
        this.query = query
        if (category == Category.MOVIE) deleteMovieDiscoverUseCase() else deleteTvDiscoverUseCase()
        getScreenData(isUserAction = true, delay = 500)
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