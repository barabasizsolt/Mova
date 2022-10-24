package com.barabasizsolt.domain.usecase.auth

import com.barabasizsolt.api.AuthResult
import com.barabasizsolt.api.AuthenticationService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull

class RegisterWithEmailAndPasswordUseCase(private val service: AuthenticationService) {

    operator fun invoke(email: String, password: String): Flow<AuthResult> =
        service.registerWithEmailAndPassWord(email = email, password = password).filterNotNull()
}
