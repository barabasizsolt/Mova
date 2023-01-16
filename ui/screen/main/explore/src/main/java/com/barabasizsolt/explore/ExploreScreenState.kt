package com.barabasizsolt.explore

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.barabasizsolt.domain.model.WatchableItem
import com.barabasizsolt.domain.usecase.screen.explore.GetExploreScreenFlowUseCase
import com.barabasizsolt.domain.usecase.screen.explore.GetExploreScreenUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import com.barabasizsolt.domain.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import org.koin.androidx.compose.get

@Composable
fun rememberExploreScreenState(
    scope: CoroutineScope = rememberCoroutineScope(),
    getExploreScreen: GetExploreScreenUseCase = get(),
    getExploreScreenFlow: GetExploreScreenFlowUseCase = get()
): ExploreScreenState = rememberSaveable(
    saver = ExploreScreenState.getSaver(
        scope = scope,
        getExploreScreen = getExploreScreen,
        getExploreScreenFlow = getExploreScreenFlow
    )
) {
    ExploreScreenState(
        scope = scope,
        getExploreScreen = getExploreScreen,
        getExploreScreenFlow = getExploreScreenFlow
    ).apply {
        getScreenData(isSearch = false)
    }
}

class ExploreScreenState(
    private val scope: CoroutineScope,
    private val getExploreScreen: GetExploreScreenUseCase,
    private val getExploreScreenFlow: GetExploreScreenFlowUseCase
) {
    var state by mutableStateOf<State>(value = State.Loading)
        private set
    var exploreContent by mutableStateOf<List<WatchableItem>?>(value = null)
        private set
    var query by mutableStateOf(value = "")
        private set

    init {
        getExploreScreenFlow().onEach {
            exploreContent = it
        }.launchIn(scope = scope)
        getScreenData(isSearch = false)
    }

    fun getScreenData(isSearch: Boolean, delay: Long = 0) {
        state = if (isSearch) State.Search else State.Loading
        scope.launch {
            delay(timeMillis = delay)
            state = when (val result = getExploreScreen(query = query)) {
                is Result.Failure -> {
                    if (!isSearch) State.Error(message = result.exception.message.orEmpty()) else State.ShowSnackBar
                }
                is Result.Success -> {
                    State.Normal
                }
            }
        }
    }

    fun onQueryChange(query: String) {
        this.query = query
        getScreenData(isSearch = true, delay = 500)
    }

    sealed class State {
        object Normal : State()
        object Loading : State()
        object Search : State()
        data class Error(val message: String) : State()
        object ShowSnackBar : State()
    }

    companion object {
        private const val QUERY_KEY: String = "query"

        fun getSaver(
            scope: CoroutineScope,
            getExploreScreen: GetExploreScreenUseCase,
            getExploreScreenFlow: GetExploreScreenFlowUseCase
        ): Saver<ExploreScreenState, *> = mapSaver(
            save = { mapOf(QUERY_KEY to it.query) },
            restore = {
                ExploreScreenState(
                    scope = scope,
                    getExploreScreen = getExploreScreen,
                    getExploreScreenFlow = getExploreScreenFlow
                ).apply {
                    query = it[QUERY_KEY] as String
                }
            }
        )
    }
}