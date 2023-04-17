package com.barabasizsolt.mova.domain.usecase.auth

import com.barabasizsolt.api.AuthenticationService

class LogOutUseCase(private val service: AuthenticationService) {

    operator fun invoke() = service.logOut()
}
