package com.barabasizsolt.domain.usecase.auth

import com.barabasizsolt.api.AuthenticationService
import kotlinx.coroutines.flow.filterNotNull

class LoginWithFacebookAccountUseCase(private val service: AuthenticationService) {

    operator fun invoke() = service.loginWithFacebookAccount().filterNotNull()
}