package ui.screen.auth

import com.barabasizsolt.mova.domain.usecase.auth.LoginWithEmailAndPasswordUseCase
import com.barabasizsolt.mova.domain.usecase.auth.RegisterWithEmailAndPasswordUseCase

internal class AuthScreenState(
    private val screenType: String,
    private val loginWithEmailAndPassword: LoginWithEmailAndPasswordUseCase,
    private val registerWithEmailAndPassword: RegisterWithEmailAndPasswordUseCase
) : BaseAuthScreenState(
    screenType = screenType,
    loginWithEmailAndPassword = loginWithEmailAndPassword,
    registerWithEmailAndPassword = registerWithEmailAndPassword
)