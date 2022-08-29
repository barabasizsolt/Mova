package com.barabasizsolt.mova.ui.screen.main.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.barabasizsolt.domain.model.HomeScreenContent
import com.barabasizsolt.domain.useCase.screen.home.GetHomeScreenFlowUseCase
import com.barabasizsolt.domain.useCase.screen.home.GetHomeScreenUseCase
import com.barabasizsolt.domain.util.Result
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeScreenModel(
    private val getHomeScreen: GetHomeScreenUseCase,
    private val getHomeScreenFlow: GetHomeScreenFlowUseCase
) : StateScreenModel<HomeScreenModel.State>(initialState = State.Normal) {

    var homeContent by mutableStateOf<HomeScreenContent?>(value = null)
        private set

    init {
        getHomeScreenFlow().onEach {
            homeContent = it
        }.launchIn(scope = coroutineScope)
        getScreenData()
    }

    private fun getScreenData() {
        mutableState.value = State.Loading
        coroutineScope.launch {
            when (val result = getHomeScreen(coroutineScope = this)) {
                is Result.Failure -> {
                    mutableState.value = State.Error(message = result.exception.message.orEmpty())
                    println("Error: ${result.exception.message.orEmpty()}")
                }
                is Result.Success -> {
                    mutableState.value = State.Normal
                }
            }
        }
    }

    sealed class State {
        object Normal : State()
        object Loading : State()
        data class Error(val message: String) : State()
    }
}