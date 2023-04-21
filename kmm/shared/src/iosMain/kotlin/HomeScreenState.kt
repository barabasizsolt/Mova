package com.barabasizsolt.mova.shared

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.barabasizsolt.mova.api.MovieService
import com.barabasizsolt.mova.domain.model.ContentItem
import com.barabasizsolt.mova.domain.model.toContentItem
import com.barabasizsolt.mova.domain.usecase.helper.movie.nowPlaying.GetNowPlayingMoviesFlowUseCase
import com.barabasizsolt.mova.domain.usecase.helper.movie.nowPlaying.GetNowPlayingMoviesUseCase
import com.barabasizsolt.mova.domain.util.Result
import com.barabasizsolt.mova.movie.implementation.MovieRemoteSource
import com.barabasizsolt.mova.movie.implementation.MovieServiceImpl
import com.barabasizsolt.mova.pager.RefreshType
import com.barabasizsolt.mova.ui.screen.base.UserAction
import com.barabasizsolt.network.implementation.BaseHttpClientImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

private val movieService: MovieService = MovieServiceImpl(
    remoteSource = MovieRemoteSource(
        httpClient = BaseHttpClientImpl(
            hostUrl = "api.themoviedb.org",
            apiKey = "93697a6983d40e793bc6b81401c77e1c",
            isDebugBuild = true
        )
    )
)

@Composable
fun rememberHomeScreenState(
    getContent: GetNowPlayingMoviesUseCase = GetNowPlayingMoviesUseCase(
        movieService = movieService
    ),
    getContentFlow: GetNowPlayingMoviesFlowUseCase = GetNowPlayingMoviesFlowUseCase(
        movieService = movieService
    )
) = remember {
    HomeScreenState(
        getContent = getContent,
        getContentFlow = getContentFlow
    )
}

open class HomeScreenState(
    private val getContent: GetNowPlayingMoviesUseCase,
    private val getContentFlow: GetNowPlayingMoviesFlowUseCase
) : CoroutineScope {

    final override val coroutineContext = SupervisorJob() + Dispatchers.Main
    private val scope = CoroutineScope(context = coroutineContext)

    var state by mutableStateOf<State>(value = State.Normal)
        protected set
    var watchableItems by mutableStateOf<List<ContentItem>>(value = emptyList())
        private set

    init {
        getContentFlow().observe {

            watchableItems = listOf(ContentItem.ItemHeader) + it.map { item -> item.toContentItem() }
        }
        getScreenData(userAction = UserAction.SwipeRefresh)
    }

    fun getScreenData(userAction: UserAction) {
        if (state !in listOf(State.Loading, State.SwipeRefresh)) {
            scope.launch {
                state = when (userAction) {
                    UserAction.SwipeRefresh -> State.SwipeRefresh
                    UserAction.TryAgain -> State.TryAgainLoading
                    else -> State.Normal
                }
                state = when (
                    val result = getContent(
                        refreshType = when {
                            userAction is UserAction.SwipeRefresh -> RefreshType.FORCE_REFRESH
                            watchableItems.isEmpty() -> RefreshType.CACHE_IF_POSSIBLE
                            else -> RefreshType.NEXT_PAGE
                        }
                    )
                ) {
                    is Result.Failure -> when {
                        userAction is UserAction.SwipeRefresh -> State.ShowSnackBar
                        watchableItems.isNotEmpty() -> State.Normal
                        else -> State.Error(message = result.exception.message.orEmpty())
                    }

                    is Result.Success -> State.Normal
                }
            }
        }
    }

    fun onClear() = coroutineContext.cancelChildren()

    private fun <T> Flow<T>.observe(action: suspend (T) -> Unit) = this.onEach(action).launchIn(scope = scope)

    sealed class State {
        object Normal : State()
        object Loading : State()
        object SwipeRefresh : State()
        data class Error(val message: String) : State()
        object ShowSnackBar : State()
        object TryAgainLoading : State()
    }
}