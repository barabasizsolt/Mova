package ui.screen.socialLogin

import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.coroutineScope
import com.barabasizsolt.mova.auth.api.AuthResult
import com.barabasizsolt.mova.domain.usecase.auth.GetIntentForGoogleAccountLoginUseCase
import com.barabasizsolt.mova.domain.usecase.auth.LoginWithFacebookAccountUseCase
import com.barabasizsolt.mova.domain.usecase.auth.LoginWithGoogleAccountUseCase
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class SocialLoginScreenState(
    private val getIntentForGoogleAccountLogin: GetIntentForGoogleAccountLoginUseCase,
    private val loginWithGoogleAccountUseCase: LoginWithGoogleAccountUseCase,
    private val loginWithFacebookAccountUseCase: LoginWithFacebookAccountUseCase
) : BaseSocialLoginScreenState() {

    override var state by mutableStateOf<State>(value = State.Normal)

    fun loginWithFacebook() {
        state = State.Loading
        coroutineScope.launch {
            loginWithFacebookAccountUseCase().onEach { result ->
                state = when (result) {
                    is AuthResult.Failure -> State.Error(message = result.error)
                    is AuthResult.Dismissed -> State.Error(message = result.error.orEmpty())
                    is AuthResult.Success -> State.Normal
                }
            }.stateIn(scope = this)
        }
    }

    fun loginWithGoogle(intent: Intent) {
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