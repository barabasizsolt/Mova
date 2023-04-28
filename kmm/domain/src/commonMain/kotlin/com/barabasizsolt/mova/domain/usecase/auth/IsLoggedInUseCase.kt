package com.barabasizsolt.mova.domain.usecase.auth

import com.barabasizsolt.mova.auth.api.AuthenticationService
import kotlinx.coroutines.flow.filterNotNull

class IsLoggedInUseCase(private val service: AuthenticationService) {

    operator fun invoke() = service.authenticationState.filterNotNull()
}