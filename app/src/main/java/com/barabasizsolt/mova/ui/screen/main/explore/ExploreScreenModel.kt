package com.barabasizsolt.mova.ui.screen.main.explore

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.barabasizsolt.domain.model.WatchableItem
import com.barabasizsolt.domain.useCase.screen.explore.GetExploreScreenFlowUseCase
import com.barabasizsolt.domain.useCase.screen.explore.GetExploreScreenUseCase
import com.barabasizsolt.domain.util.Result
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ExploreScreenModel(
    private val getExploreScreen: GetExploreScreenUseCase,
    private val getExploreScreenFlow: GetExploreScreenFlowUseCase
) : StateScreenModel<ExploreScreenModel.State>(initialState = State.Normal) {

    var exploreContent by mutableStateOf<List<WatchableItem>?>(value = null)
        private set

    init {
        getExploreScreenFlow().onEach {
            exploreContent = it
        }.launchIn(scope = coroutineScope)
        getScreenData(swipeRefresh = false)
    }

   fun getScreenData(swipeRefresh: Boolean) {
        mutableState.value = if (swipeRefresh) State.SwipeRefresh else State.Loading
        coroutineScope.launch {
            when (val result = getExploreScreen()) {
                is Result.Failure -> {
                    mutableState.value = if (!swipeRefresh) State.Error(message = result.exception.message.orEmpty()) else State.ShowSnackBar
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
        object SwipeRefresh : State()
        data class Error(val message: String) : State()
        object ShowSnackBar : State()
    }
}