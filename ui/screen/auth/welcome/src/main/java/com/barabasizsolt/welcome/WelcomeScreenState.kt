package com.barabasizsolt.welcome

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.barabasizsolt.util.Event

@Composable
fun rememberWelcomeScreenState() = remember { WelcomeScreenState() }

class WelcomeScreenState {

    var action by mutableStateOf<Event<Action>?>(value = null)
        private set

    fun navigateToAuth() {
        action = Event(data = Action.NavigateToAuth)
    }

    sealed class Action {
        object NavigateToAuth : Action()
    }
}