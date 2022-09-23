package com.barabasizsolt.login

import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.barabasizsolt.api.AuthResult
import com.barabasizsolt.domain.usecase.auth.GetIntentForGoogleAccountLoginUseCase
import com.barabasizsolt.domain.usecase.auth.LoginWithGoogleAccountUseCase
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LoginScreenModel(
    private val getIntentForGoogleAccountLogin: GetIntentForGoogleAccountLoginUseCase,
    private val loginWithGoogleAccountUseCase: LoginWithGoogleAccountUseCase
) : StateScreenModel<LoginScreenModel.State>(initialState = State.Normal) {

    var action by mutableStateOf<Action?>(value = null)
        private set

    fun loginWithGoogle(intent: Intent) {
        mutableState.value = State.Loading
        coroutineScope.launch {
            loginWithGoogleAccountUseCase(intent = intent).onEach { result ->
                when (result) {
                    is AuthResult.Success -> {
                        action = Action.NavigateToHome
                        mutableState.value = State.Normal
                    }
                    is AuthResult.Failure -> {
                        mutableState.value = State.Error(message = "Google Login failed: ${result.error}")
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
        object LoggedIn : State()
    }

    sealed class Action {

        object NavigateToHome : Action()

        object NavigateToRegister : Action()
    }

}