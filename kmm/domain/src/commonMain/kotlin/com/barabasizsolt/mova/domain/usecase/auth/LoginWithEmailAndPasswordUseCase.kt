package com.barabasizsolt.mova.domain.usecase.auth

import com.barabasizsolt.mova.auth.api.AuthResult
import com.barabasizsolt.mova.auth.api.AuthenticationService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull

class LoginWithEmailAndPasswordUseCase(private val service: AuthenticationService) {

    operator fun invoke(email: String, password: String): Flow<AuthResult> =
        service.loginWithEmailAndPassword(email = email, password = password).filterNotNull()
}
