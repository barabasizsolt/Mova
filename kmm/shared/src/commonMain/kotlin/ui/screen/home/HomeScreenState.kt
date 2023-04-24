package ui.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.barabasizsolt.mova.domain.model.HomeScreenContent
import com.barabasizsolt.mova.domain.model.isEmpty
import com.barabasizsolt.mova.domain.usecase.screen.home.GetHomeScreenFlowUseCase
import com.barabasizsolt.mova.domain.usecase.screen.home.GetHomeScreenUseCase
import com.barabasizsolt.mova.domain.util.Result
import com.barabasizsolt.mova.pager.RefreshType
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ui.screen.base.BaseScreenState
import ui.screen.base.UserAction

class HomeScreenState(
    private val getHomeScreen: GetHomeScreenUseCase,
    getHomeScreenFlow: GetHomeScreenFlowUseCase,
) : BaseScreenState(), ScreenModel {

    var homeContent by mutableStateOf(value = HomeScreenContent.createEmptyHomeScreenContent())
        private set

    init {
        getScreenData(userAction = UserAction.Normal)
        getHomeScreenFlow().onEach {
            homeContent = it
        }.launchIn(scope = coroutineScope)
    }

    override fun getScreenData(userAction: UserAction, delay: Long) {
        state = when (userAction) {
            UserAction.SwipeRefresh -> State.SwipeRefresh
            else -> State.Loading
        }
        coroutineScope.launch {
            state = when (
                val result = getHomeScreen(
                    refreshType = when (userAction) {
                        is UserAction.SwipeRefresh -> RefreshType.FORCE_REFRESH
                        else -> RefreshType.CACHE_IF_POSSIBLE
                    }
                )
            ) {
                is Result.Failure -> when {
                    userAction is UserAction.Normal && homeContent.isEmpty() ->
                        State.Error(message = result.exception.message.orEmpty())
                    userAction is UserAction.Normal && !homeContent.isEmpty() ->
                        State.Normal
                    userAction is UserAction.SwipeRefresh ->
                        State.ShowSnackBar
                    else -> State.Error(message = result.exception.message.orEmpty())
                }
                is Result.Success -> State.Normal
            }
        }
    }
}