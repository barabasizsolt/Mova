package com.barabasizsolt.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.barabasizsolt.base.BaseScreenState
import com.barabasizsolt.domain.model.HomeScreenContent
import com.barabasizsolt.domain.usecase.screen.home.GetHomeScreenFlowUseCase
import com.barabasizsolt.domain.usecase.screen.home.GetHomeScreenUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import com.barabasizsolt.domain.util.Result
import com.barabasizsolt.util.Event
import com.barabasizsolt.util.RefreshType
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
    private val getHomeScreenFlow: GetHomeScreenFlowUseCase,
) : BaseScreenState() {

    var action by mutableStateOf<Event<Action>?>(value = null)
        private set
    var homeContent by mutableStateOf<HomeScreenContent?>(value = null)
        private set

    init {
        getHomeScreenFlow().onEach {
            homeContent = it
        }.launchIn(scope = scope)
        getScreenData(isUserAction = false)
    }

    override fun getScreenData(isUserAction: Boolean, delay: Long) {
        state = if (isUserAction) State.UserAction else State.Loading
        scope.launch {
            state = when (
                val result = getHomeScreen(
                    coroutineScope = this,
                    refreshType = if (isUserAction) RefreshType.FORCE_REFRESH else RefreshType.CACHE_IF_POSSIBLE
                )
            ) {
                is Result.Failure -> if (!isUserAction) State.Error(message = result.exception.message.orEmpty()) else State.ShowSnackBar
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

    sealed class Action {
        object SeeAllPopularMovies : Action()
        object SeeAllPopularPeople : Action()
        object SeeAllNowPlayingMovies : Action()
        object SeeAllTopRatedMovies : Action()
    }
}