package com.barabasizsolt.domain.usecase.auth

import android.content.Intent
import com.barabasizsolt.api.AuthenticationService
import kotlinx.coroutines.flow.filterNotNull

class LoginWithGoogleAccountUseCase(private val service: AuthenticationService) {

    operator fun invoke(intent: Intent) = service.loginWithGoogleAccount(intent = intent).filterNotNull()
}
