package com.barabasizsolt.login

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.barabasizsolt.api.AuthResult
import com.barabasizsolt.domain.usecase.auth.GetIntentForGoogleAccountLoginUseCase
import com.barabasizsolt.domain.usecase.auth.LoginWithGoogleAccountUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import com.barabasizsolt.util.Event

@Composable
fun rememberLoginScreenState(
    scope: CoroutineScope = rememberCoroutineScope(),
    getIntentForGoogleAccountLogin: GetIntentForGoogleAccountLoginUseCase = get(),
    loginWithGoogleAccountUseCase: LoginWithGoogleAccountUseCase = get()
): LoginScreenState = rememberSaveable(
    saver = LoginScreenState.getSaver(
        scope = scope,
        getIntentForGoogleAccountLogin = getIntentForGoogleAccountLogin,
        loginWithGoogleAccountUseCase = loginWithGoogleAccountUseCase
    )
) {
    LoginScreenState(
        scope = scope,
        getIntentForGoogleAccountLogin = getIntentForGoogleAccountLogin,
        loginWithGoogleAccountUseCase = loginWithGoogleAccountUseCase
    )
}

class LoginScreenState(
    private val scope: CoroutineScope,
    private val getIntentForGoogleAccountLogin: GetIntentForGoogleAccountLoginUseCase,
    private val loginWithGoogleAccountUseCase: LoginWithGoogleAccountUseCase
) {

    var state by mutableStateOf<State>(value = State.Normal)
        private set
    var action by mutableStateOf<Event<Action>?>(value = null)
        private set
    var email by mutableStateOf(value = "")
        private set
    var password by mutableStateOf(value = "")
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

    sealed class State {
        object Normal : State()
        object Loading : State()
        data class Error(val message: String) : State()
    }

    sealed class Action {
        object NavigateToHome : Action()
        object NavigateToRegister : Action()
    }

    companion object {
        private const val EMAIL_KEY: String = "email"
        private const val PASSWORD_KEY: String = "password"

        fun getSaver(
            scope: CoroutineScope,
            getIntentForGoogleAccountLogin: GetIntentForGoogleAccountLoginUseCase,
            loginWithGoogleAccountUseCase: LoginWithGoogleAccountUseCase
        ): Saver<LoginScreenState, *> = mapSaver(
            save = { mapOf(EMAIL_KEY to it.email, PASSWORD_KEY to it.password) },
            restore = {
                LoginScreenState(
                    scope = scope,
                    getIntentForGoogleAccountLogin = getIntentForGoogleAccountLogin,
                    loginWithGoogleAccountUseCase = loginWithGoogleAccountUseCase
                ).apply {
                    email = it[EMAIL_KEY] as String
                    password = it[PASSWORD_KEY] as String
                }
            }
        )
    }
}