package com.barabasizsolt.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.barabasizsolt.domain.model.HomeScreenContent
import com.barabasizsolt.domain.usecase.screen.home.GetHomeScreenFlowUseCase
import com.barabasizsolt.domain.usecase.screen.home.GetHomeScreenUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import com.barabasizsolt.domain.util.Result
import kotlinx.coroutines.CoroutineScope
import org.koin.androidx.compose.get

@Composable
fun rememberHomeScreenState(
    scope: CoroutineScope = rememberCoroutineScope(),
    getHomeScreen: GetHomeScreenUseCase = get(),
    getHomeScreenFlow: GetHomeScreenFlowUseCase = get()
): HomeScreenState = remember {
    HomeScreenState(
        scope = scope,
        getHomeScreen = getHomeScreen,
        getHomeScreenFlow = getHomeScreenFlow
    ).apply {
        getScreenData(swipeRefresh = false)
    }
}

class HomeScreenState(
    private val scope: CoroutineScope,
    private val getHomeScreen: GetHomeScreenUseCase,
    private val getHomeScreenFlow: GetHomeScreenFlowUseCase
) {

    var state by mutableStateOf<State>(value = State.Loading)
        private set
    var homeContent by mutableStateOf<HomeScreenContent?>(value = null)
        private set

    init {
        getHomeScreenFlow().onEach {
            homeContent = it
        }.launchIn(scope = scope)
        getScreenData(swipeRefresh = false)
    }

    fun getScreenData(swipeRefresh: Boolean) {
        state = if (swipeRefresh) State.SwipeRefresh else State.Loading
        scope.launch {
            state = when (val result = getHomeScreen(coroutineScope = this)) {
                is Result.Failure -> {
                    if (!swipeRefresh) State.Error(message = result.exception.message.orEmpty()) else State.ShowSnackBar
                }
                is Result.Success -> {
                    State.Normal
                }
            }
        }
    }

    sealed class State {
        object Normal : State()
        object Loading : State()
        object SwipeRefresh : State()
        data class Error(val message: String) : State()
        object ShowSnackBar : State()
    }
}