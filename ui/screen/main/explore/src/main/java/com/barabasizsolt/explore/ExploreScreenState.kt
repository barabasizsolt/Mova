package com.barabasizsolt.explore

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.barabasizsolt.base.BaseScreenState
import com.barabasizsolt.domain.model.WatchableItem
import com.barabasizsolt.domain.usecase.screen.explore.GetExploreScreenFlowUseCase
import com.barabasizsolt.domain.usecase.screen.explore.GetExploreScreenUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import com.barabasizsolt.domain.util.Result
import kotlinx.coroutines.delay
import org.koin.androidx.compose.get

@Composable
fun rememberExploreScreenState(
    getExploreScreen: GetExploreScreenUseCase = get(),
    getExploreScreenFlow: GetExploreScreenFlowUseCase = get()
): ExploreScreenState = rememberSaveable(
    saver = ExploreScreenState.getSaver(
        getExploreScreen = getExploreScreen,
        getExploreScreenFlow = getExploreScreenFlow
    )
) {
    ExploreScreenState(
        getExploreScreen = getExploreScreen,
        getExploreScreenFlow = getExploreScreenFlow
    ).apply {
        getScreenData(isUserAction = false)
    }
}

class ExploreScreenState(
    private val getExploreScreen: GetExploreScreenUseCase,
    private val getExploreScreenFlow: GetExploreScreenFlowUseCase
) : BaseScreenState() {
    var exploreContent by mutableStateOf<List<WatchableItem>?>(value = null)
        private set
    var query by mutableStateOf(value = "")
        private set

    init {
        getExploreScreenFlow().onEach {
            exploreContent = it
        }.launchIn(scope = scope)
        getScreenData(isUserAction = false)
    }

    override fun getScreenData(isUserAction: Boolean, delay: Long) {
        state = if (isUserAction) State.UserAction else State.Loading
        scope.launch {
            delay(timeMillis = delay)
            state = when (val result = getExploreScreen(query = query)) {
                is Result.Failure -> {
                    if (!isUserAction) State.Error(message = result.exception.message.orEmpty()) else State.ShowSnackBar
                }
                is Result.Success -> {
                    State.Normal
                }
            }
        }
    }

    fun onQueryChange(query: String) {
        this.query = query
        getScreenData(isUserAction = true, delay = 500)
    }

    companion object {
        private const val QUERY_KEY: String = "query"

        fun getSaver(
            getExploreScreen: GetExploreScreenUseCase,
            getExploreScreenFlow: GetExploreScreenFlowUseCase
        ): Saver<ExploreScreenState, *> = getBaseSaver(
            save = { mapOf(QUERY_KEY to it.query) },
            restore = {
                ExploreScreenState(
                    getExploreScreen = getExploreScreen,
                    getExploreScreenFlow = getExploreScreenFlow
                ).apply {
                    query = it[QUERY_KEY] as String
                }
            }
        )
    }
}