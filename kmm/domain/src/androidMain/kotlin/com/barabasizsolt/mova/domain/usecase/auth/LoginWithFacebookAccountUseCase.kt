package com.barabasizsolt.mova.domain.usecase.auth

import com.barabasizsolt.mova.auth.api.AndroidAuthenticationService
import kotlinx.coroutines.flow.filterNotNull

class LoginWithFacebookAccountUseCase(private val service: AndroidAuthenticationService) {

    operator fun invoke() = service.loginWithFacebookAccount().filterNotNull()
}