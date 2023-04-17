package com.barabasizsolt.mova.domain.usecase.auth

import com.barabasizsolt.api.AuthResult
import com.barabasizsolt.api.AuthenticationService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull

class ResetPasswordUseCase(private val service: AuthenticationService) {

    operator fun invoke(email: String): Flow<AuthResult> =
        service.resetPassword(email = email).filterNotNull()
}
