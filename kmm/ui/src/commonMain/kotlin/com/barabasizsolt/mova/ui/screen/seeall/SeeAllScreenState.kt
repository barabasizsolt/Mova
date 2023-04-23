package com.barabasizsolt.mova.ui.screen.seeall

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.barabasizsolt.mova.domain.model.ContentItem
import com.barabasizsolt.mova.domain.usecase.screen.seeall.GetSeeAllScreenFlowUseCase
import com.barabasizsolt.mova.domain.usecase.screen.seeall.GetSeeAllScreenUseCase
import com.barabasizsolt.mova.domain.util.Result
import com.barabasizsolt.mova.pager.RefreshType
import com.barabasizsolt.mova.ui.screen.base.BaseScreenState
import com.barabasizsolt.mova.ui.screen.base.UserAction
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SeeAllScreenState(
    val contentType: String,
    private val getSeeAllScreenUseCase: GetSeeAllScreenUseCase,
    getSeeAllScreenFlowUseCase: GetSeeAllScreenFlowUseCase
) : BaseScreenState(), ScreenModel {
    var watchableItems by mutableStateOf<List<ContentItem>>(value = emptyList())
        private set

    init {
        getSeeAllScreenFlowUseCase(contentType = contentType).onEach {
            watchableItems = listOf(ContentItem.ItemHeader) + it
        }.launchIn(scope = coroutineScope)
    }

    override fun getScreenData(userAction: UserAction, delay: Long) {
        if (state !in listOf(State.Loading, State.SwipeRefresh)) {
            coroutineScope.launch {
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
}