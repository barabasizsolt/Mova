package ui.screen.socialLogin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel

abstract class BaseSocialLoginScreenState : ScreenModel {

    open var state by mutableStateOf<State>(value = State.Normal)

    open fun resetState() { state = State.Normal
    }

    sealed class State {
        object Normal : State()
        object Loading : State()
        data class Error(val message: String) : State()
    }
}