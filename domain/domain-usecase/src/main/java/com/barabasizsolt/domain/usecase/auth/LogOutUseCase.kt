package com.barabasizsolt.domain.usecase.auth

import com.barabasizsolt.api.AuthenticationService

class LogOutUseCase(private val service: AuthenticationService) {

    operator fun invoke() = service.logOut()
}
