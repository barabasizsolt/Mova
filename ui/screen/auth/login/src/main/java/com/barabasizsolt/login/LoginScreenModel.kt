package com.barabasizsolt.login

import cafe.adriel.voyager.core.model.StateScreenModel
import com.barabasizsolt.domain.usecase.auth.GetIntentForGoogleAccountLoginUseCase
import com.barabasizsolt.domain.usecase.auth.LoginWithGoogleAccountUseCase

class LoginScreenModel(
    private val getIntentForGoogleAccountLogin: GetIntentForGoogleAccountLoginUseCase,
    private val loginWithGoogleAccountUseCase: LoginWithGoogleAccountUseCase
) : StateScreenModel<LoginScreenModel.State>(initialState = State.Normal) {

    sealed class State {
        object Normal : State()
        object Loading : State()
        data class Error(val message: String) : State()
        object LoggedIn : State()
    }
}