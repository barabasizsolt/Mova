package com.barabasizsolt.mova.domain.usecase.auth

import com.barabasizsolt.mova.auth.api.AuthenticationService

class ResetPasswordUseCase(private val service: AuthenticationService) {

    suspend operator fun invoke(email: String) = service.resetPassword(email = email)
}
