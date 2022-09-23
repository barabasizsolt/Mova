package com.barabasizsolt.explore

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.barabasizsolt.domain.model.WatchableItem
import com.barabasizsolt.domain.useCase.screen.explore.GetExploreScreenFlowUseCase
import com.barabasizsolt.domain.useCase.screen.explore.GetExploreScreenUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import com.barabasizsolt.domain.util.Result

class ExploreScreenModel(
    private val getExploreScreen: GetExploreScreenUseCase,
    private val getExploreScreenFlow: GetExploreScreenFlowUseCase
) : StateScreenModel<ExploreScreenModel.State>(initialState = State.Normal) {

    var exploreContent by mutableStateOf<List<WatchableItem>?>(value = null)
        private set
    var query by mutableStateOf(value = "")
        private set

    init {
        getExploreScreenFlow().onEach {
            exploreContent = it
        }.launchIn(scope = coroutineScope)
        getScreenData(isSearch = false)
    }

    fun getScreenData(isSearch: Boolean) {
        mutableState.value = if (isSearch) State.Search else State.Loading
        coroutineScope.launch {
            when (val result = getExploreScreen(query = query)) {
                is Result.Failure -> {
                    mutableState.value = if (!isSearch) State.Error(message = result.exception.message.orEmpty()) else State.ShowSnackBar
                }
                is Result.Success -> {
                    mutableState.value = State.Normal
                }
            }
        }
    }

    fun onQueryChange(query: String) {
        this.query = query
        getScreenData(isSearch = true)
    }

    sealed class State {
        object Normal : State()
        object Loading : State()
        object Search : State()
        data class Error(val message: String) : State()
        object ShowSnackBar : State()
    }
}