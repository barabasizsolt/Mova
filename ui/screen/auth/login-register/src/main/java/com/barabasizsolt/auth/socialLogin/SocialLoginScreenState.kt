package com.barabasizsolt.auth.socialLogin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.barabasizsolt.util.Event

@Composable
fun rememberSocialLoginScreenState() = remember { SocialLoginScreenState() }

class SocialLoginScreenState {

    var action by mutableStateOf<Event<Action>?>(value = null)
        private set

    fun navigateToAuth() {
        action = Event(data = Action.NavigateToAuth)
    }

    sealed class Action {
        object Authenticate: Action()
        object NavigateToAuth : Action()
    }
}