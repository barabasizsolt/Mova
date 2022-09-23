package com.barabasizsolt.domain.usecase.auth

import com.barabasizsolt.api.AuthenticationService

class IsLoggedInUseCase(private val service: AuthenticationService) {

    operator fun invoke() = service.isLogged()
}