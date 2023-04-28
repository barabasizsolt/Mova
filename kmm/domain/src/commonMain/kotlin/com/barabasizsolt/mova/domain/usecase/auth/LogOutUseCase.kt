package com.barabasizsolt.mova.domain.usecase.auth

import com.barabasizsolt.mova.auth.api.AuthenticationService

class LogOutUseCase(private val service: AuthenticationService) {

    suspend operator fun invoke() = service.logOut()
}
