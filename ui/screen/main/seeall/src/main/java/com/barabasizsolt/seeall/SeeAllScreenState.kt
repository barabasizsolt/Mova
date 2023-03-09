package com.barabasizsolt.seeall

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.barabasizsolt.base.BaseScreenState
import com.barabasizsolt.base.UserAction
import com.barabasizsolt.domain.model.ContentItem
import com.barabasizsolt.domain.usecase.screen.seeall.GetSeeAllScreenFlowUseCase
import com.barabasizsolt.domain.usecase.screen.seeall.GetSeeAllScreenUseCase
import com.barabasizsolt.domain.util.result.Result
import com.barabasizsolt.pagination.api.RefreshType
import com.barabasizsolt.util.Event
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@Composable
fun rememberSeeAllScreenState(
    contentType: String,
    getSeeAllScreenUseCase: GetSeeAllScreenUseCase = get(),
    getSeeAllScreenFlowUseCase: GetSeeAllScreenFlowUseCase = get()
) = remember {
    SeeAllScreenState(
        contentType = contentType,
        getSeeAllScreenUseCase = getSeeAllScreenUseCase,
        getSeeAllScreenFlowUseCase = getSeeAllScreenFlowUseCase
    )
}

class SeeAllScreenState(
    val contentType: String,
    private val getSeeAllScreenUseCase: GetSeeAllScreenUseCase,
    private val getSeeAllScreenFlowUseCase: GetSeeAllScreenFlowUseCase
) : BaseScreenState() {

    var action by mutableStateOf<Event<Action>?>(value = null)
        private set
    var watchableItems by mutableStateOf<List<ContentItem>>(value = emptyList())
        private set

    init {
        getSeeAllScreenFlowUseCase(contentType = contentType).observe { watchableItems = it }
    }

    override fun getScreenData(userAction: UserAction, delay: Long) {
        if (state !in listOf(State.Loading, State.SwipeRefresh)) {
            scope.launch {
                state = when (userAction) {
                    UserAction.SwipeRefresh -> State.SwipeRefresh
                    UserAction.TryAgain -> State.TryAgainLoading
                    else -> State.Normal
                }
                state = when (
                    val result = getSeeAllScreenUseCase(
                        contentType = contentType,
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

    fun onUpClicked() {
        action = Event(data = Action.NavigateUp)
    }

    sealed class Action {
        object NavigateUp : Action()
    }
}