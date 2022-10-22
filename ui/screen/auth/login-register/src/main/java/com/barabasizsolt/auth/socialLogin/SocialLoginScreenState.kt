package com.barabasizsolt.auth.socialLogin

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.barabasizsolt.api.AuthResult
import com.barabasizsolt.domain.usecase.auth.GetIntentForGoogleAccountLoginUseCase
import com.barabasizsolt.domain.usecase.auth.LoginWithGoogleAccountUseCase
import com.barabasizsolt.util.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@Composable
fun rememberSocialLoginScreenState(
    scope: CoroutineScope = rememberCoroutineScope(),
    getIntentForGoogleAccountLogin: GetIntentForGoogleAccountLoginUseCase = get(),
    loginWithGoogleAccountUseCase: LoginWithGoogleAccountUseCase = get()
) = remember {
    SocialLoginScreenState(
        scope = scope,
        getIntentForGoogleAccountLogin = getIntentForGoogleAccountLogin,
        loginWithGoogleAccountUseCase = loginWithGoogleAccountUseCase
    )
}

class SocialLoginScreenState(
    private val scope: CoroutineScope,
    private val getIntentForGoogleAccountLogin: GetIntentForGoogleAccountLoginUseCase,
    private val loginWithGoogleAccountUseCase: LoginWithGoogleAccountUseCase
) {

    var state by mutableStateOf<State>(value = State.Normal)
        private set
    var action by mutableStateOf<Event<Action>?>(value = null)
        private set

    fun loginWithGoogle(intent: Intent) {
        state = State.Loading
        scope.launch {
            loginWithGoogleAccountUseCase(intent = intent).onEach { result ->
                when (result) {
                    is AuthResult.Success -> {
                        state = State.Normal
                        action = Event(Action.NavigateToHome)
                    }
                    is AuthResult.Failure -> {
                        state = State.Error(message = "Google Login failed: ${result.error}")
                    }
                }
            }.stateIn(scope = this)
        }
    }

    fun getIntentForGoogleLogin(): Intent = getIntentForGoogleAccountLogin()

    fun onSignInClicked() {
        action = Event(data = Action.NavigateToLogin)
    }

    fun onSignUpClicked() {
        action = Event(data = Action.NavigateToRegister)
    }

    sealed class State {
        object Normal: State()
        object Loading: State()
        data class Error(val message: String): State()
    }

    sealed class Action {
        object NavigateToHome: Action()
        object NavigateToLogin : Action()
        object NavigateToRegister : Action()
    }
}