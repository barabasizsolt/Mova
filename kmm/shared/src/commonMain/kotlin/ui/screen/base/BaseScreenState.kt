package ui.screen.base

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

abstract class BaseScreenState {

    var state by mutableStateOf<State>(value = State.Normal)
        protected set

    abstract fun getScreenData(userAction: UserAction, delay: Long = 0)

    sealed class State {
        object Normal : State()
        object Loading : State()
        object SwipeRefresh : State()
        object SearchLoading : State()
        data class Error(val message: String) : State()
        object ShowSnackBar : State()
        object TryAgainLoading : State()
    }
}