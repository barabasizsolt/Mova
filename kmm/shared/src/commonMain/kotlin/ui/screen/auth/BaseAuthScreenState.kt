package ui.screen.auth

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.barabasizsolt.mova.auth.api.AuthResult
import com.barabasizsolt.mova.domain.usecase.auth.LoginWithEmailAndPasswordUseCase
import com.barabasizsolt.mova.domain.usecase.auth.RegisterWithEmailAndPasswordUseCase
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class ScreenType {
    LOGIN, REGISTER
}

internal open class BaseAuthScreenState(
    private val screenType: String,
    private val loginWithEmailAndPassword: LoginWithEmailAndPasswordUseCase,
    private val registerWithEmailAndPassword: RegisterWithEmailAndPasswordUseCase
) : ScreenModel {

    open var state by mutableStateOf<State>(value = State.Normal)
    open var screenProperty by mutableStateOf(
        value = when (screenType) {
            ScreenType.LOGIN.name -> ScreenProperty.Login
            ScreenType.REGISTER.name -> ScreenProperty.Register
            else -> null
        }
    )
    open var email by mutableStateOf(value = "")
    open var password by mutableStateOf(value = "")
    val isAuthEnabled by derivedStateOf { email.isNotEmpty() && password.isNotEmpty() }

    fun authenticate() {
        if (isAuthEnabled) {
            state = State.Loading
            coroutineScope.launch {
                if (screenProperty is ScreenProperty.Login) {
                    loginWithEmailAndPassword(email = email, password = password)
                } else {
                    registerWithEmailAndPassword(email = email, password = password)
                }.onEach { authResult ->
                    state = when (authResult) {
                        is AuthResult.Failure -> State.Error(message = authResult.error)
                        is AuthResult.Success, is AuthResult.Dismissed -> State.Normal
                    }

                }.stateIn(scope = this)
            }
        }
    }

    fun changeAuthScreen() {
        screenProperty = if (screenProperty is ScreenProperty.Login) ScreenProperty.Register else ScreenProperty.Login
    }

    fun onEmailChange(value: String) { email = value }

    fun onPasswordChange(value: String) { password = value }

    fun resetState() { state = State.Normal
    }

    sealed class State {
        object Normal : State()
        object Loading : State()
        data class Error(val message: String) : State()
    }

    sealed class ScreenProperty {
        abstract val screenTitle: String
        abstract val authButtonText: String
        abstract val authFooterQuestion: String
        abstract val authFooterText: String

        object Login : ScreenProperty() {
            override val screenTitle: String = "Login to Your Account"
            override val authButtonText: String = SIGN_IN
            override val authFooterQuestion: String = "Don't have an account?"
            override val authFooterText: String = SIGN_UP
        }

        object Register : ScreenProperty() {
            override val screenTitle: String = "Create Your Account"
            override val authButtonText: String = SIGN_UP
            override val authFooterQuestion: String = "Already have an account?"
            override val authFooterText: String = SIGN_IN
        }
    }

    companion object {
        private const val SIGN_UP: String = "Sign up"
        private const val SIGN_IN: String = "Sign in"
    }
}