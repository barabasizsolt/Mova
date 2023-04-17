package com.barabasizsolt.mova.ui.screen.seeall

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.barabasizsolt.mova.domain.model.ContentItem
import com.barabasizsolt.mova.domain.usecase.screen.seeall.GetSeeAllScreenFlowUseCase
import com.barabasizsolt.mova.domain.usecase.screen.seeall.GetSeeAllScreenUseCase
import com.barabasizsolt.mova.pager.RefreshType
import com.barabasizsolt.mova.ui.screen.base.BaseScreenState
import com.barabasizsolt.mova.ui.screen.base.UserAction
import com.barabasizsolt.mova.ui.util.Event
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import com.barabasizsolt.mova.domain.util.Result

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
    getSeeAllScreenFlowUseCase: GetSeeAllScreenFlowUseCase
) : BaseScreenState() {

    var action by mutableStateOf<Event<Action>?>(value = null)
        private set
    var watchableItems by mutableStateOf<List<ContentItem>>(value = emptyList())
        private set

    init {
        getSeeAllScreenFlowUseCase(contentType = contentType).observe {
            watchableItems = listOf(ContentItem.ItemHeader) + it
        }
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
                        watchableItems.isNotEmpty() -> State.Normal.also {
                            println("<<ERR: ${result.exception}")
                        }
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

    fun onMovieClicked(id: Int) {
        action = Event(data = Action.OnMovieClicked(id = id))
    }

    sealed class Action {
        object NavigateUp : Action()
        data class OnMovieClicked(val id: Int) : Action()
    }
}