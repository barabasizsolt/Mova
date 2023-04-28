package ui.screen.auth

import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.coroutineScope
import com.barabasizsolt.mova.auth.api.AuthResult
import com.barabasizsolt.mova.domain.usecase.auth.GetIntentForGoogleAccountLoginUseCase
import com.barabasizsolt.mova.domain.usecase.auth.LoginWithEmailAndPasswordUseCase
import com.barabasizsolt.mova.domain.usecase.auth.LoginWithFacebookAccountUseCase
import com.barabasizsolt.mova.domain.usecase.auth.LoginWithGoogleAccountUseCase
import com.barabasizsolt.mova.domain.usecase.auth.RegisterWithEmailAndPasswordUseCase
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class AuthScreenState(
    private val screenType: String,
    private val loginWithEmailAndPassword: LoginWithEmailAndPasswordUseCase,
    private val registerWithEmailAndPassword: RegisterWithEmailAndPasswordUseCase,
    private val getIntentForGoogleAccountLogin: GetIntentForGoogleAccountLoginUseCase,
    private val loginWithGoogleAccountUseCase: LoginWithGoogleAccountUseCase,
    private val loginWithFacebookAccountUseCase: LoginWithFacebookAccountUseCase
) : BaseAuthScreenState(
    screenType = screenType,
    loginWithEmailAndPassword = loginWithEmailAndPassword,
    registerWithEmailAndPassword = registerWithEmailAndPassword
) {

    override var state by mutableStateOf<State>(value = State.Normal)

    fun authenticateWithFacebook() {
        state = State.Loading
        coroutineScope.launch {
            loginWithFacebookAccountUseCase().onEach { result ->
                state = when (result) {
                    is AuthResult.Dismissed -> State.Error(message = result.error.orEmpty())
                    is AuthResult.Failure -> State.Error(message = result.error)
                    is AuthResult.Success -> State.Normal
                }
            }.stateIn(scope = this)
        }
    }

    fun authenticateWithGoogle(intent: Intent) {
        state = State.Loading
        coroutineScope.launch {
            loginWithGoogleAccountUseCase(intent = intent).onEach { result ->
                state = when (result) {
                    is AuthResult.Failure -> State.Error(message = result.error)
                    is AuthResult.Success, is AuthResult.Dismissed -> State.Normal
                }
            }.stateIn(scope = this)
        }
    }

    fun getIntentForGoogleLogin(): Intent = getIntentForGoogleAccountLogin()
}