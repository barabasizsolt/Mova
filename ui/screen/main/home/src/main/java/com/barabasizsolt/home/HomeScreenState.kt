package com.barabasizsolt.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.barabasizsolt.base.BaseScreenState
import com.barabasizsolt.base.UserAction
import com.barabasizsolt.domain.model.HomeScreenContent
import com.barabasizsolt.domain.model.isEmpty
import com.barabasizsolt.domain.usecase.screen.home.GetHomeScreenFlowUseCase
import com.barabasizsolt.domain.usecase.screen.home.GetHomeScreenUseCase
import kotlinx.coroutines.launch
import com.barabasizsolt.domain.util.result.Result
import com.barabasizsolt.pagination.RefreshType
import com.barabasizsolt.util.Event
import org.koin.androidx.compose.get

@Composable
fun rememberHomeScreenState(
    getHomeScreen: GetHomeScreenUseCase = get(),
    getHomeScreenFlow: GetHomeScreenFlowUseCase = get()
): HomeScreenState = remember {
    HomeScreenState(
        getHomeScreen = getHomeScreen,
        getHomeScreenFlow = getHomeScreenFlow
    )
}

class HomeScreenState(
    private val getHomeScreen: GetHomeScreenUseCase,
    getHomeScreenFlow: GetHomeScreenFlowUseCase,
) : BaseScreenState() {

    var action by mutableStateOf<Event<Action>?>(value = null)
        private set
    var homeContent by mutableStateOf(value = HomeScreenContent.createEmptyHomeScreenContent())
        private set

    init {
        getScreenData(userAction = UserAction.Normal)
        getHomeScreenFlow().observe {
            homeContent = it
        }
    }

    override fun getScreenData(userAction: UserAction, delay: Long) {
        state = when (userAction) {
            UserAction.SwipeRefresh -> State.SwipeRefresh
            else -> State.Loading
        }
        scope.launch {
            state = when (
                val result = getHomeScreen(
                    refreshType = when (userAction) {
                        is UserAction.SwipeRefresh -> RefreshType.FORCE_REFRESH
                        else -> RefreshType.CACHE_IF_POSSIBLE
                    }
                )
            ) {
                is Result.Failure -> when {
                    userAction is UserAction.Normal && homeContent.isEmpty() ->
                        State.Error(message = result.exception.message.orEmpty())
                    userAction is UserAction.Normal && !homeContent.isEmpty() ->
                        State.Normal
                    userAction is UserAction.SwipeRefresh ->
                        State.ShowSnackBar
                    else -> State.Error(message = result.exception.message.orEmpty())
                }
                is Result.Success -> State.Normal
            }
        }
    }

    fun onSeeAllPopularMoviesClicked() {
        action = Event(data = Action.SeeAllPopularMovies)
    }

    fun onSeeAllPopularPeopleClicked() {
        action = Event(data = Action.SeeAllPopularPeople)
    }

    fun onSeeAllNowPlayingMoviesClicked() {
        action = Event(data = Action.SeeAllNowPlayingMovies)
    }

    fun onSeeAllTopRatedMoviesClicked() {
        action = Event(data = Action.SeeAllTopRatedMovies)
    }

    fun onMovieClicked(id: Int) {
        action = Event(data = Action.OnMovieClicked(id = id))
    }

    sealed class Action {
        object SeeAllPopularMovies : Action()
        object SeeAllPopularPeople : Action()
        object SeeAllNowPlayingMovies : Action()
        object SeeAllTopRatedMovies : Action()
        data class OnMovieClicked(val id: Int) : Action()
    }
}