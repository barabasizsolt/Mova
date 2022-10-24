package com.barabasizsolt.domain.usecase.auth

import com.barabasizsolt.api.AuthenticationService
import kotlinx.coroutines.flow.filterNotNull

class IsLoggedInUseCase(private val service: AuthenticationService) {

    operator fun invoke() = service.authenticationState.filterNotNull()
}