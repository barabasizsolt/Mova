package com.barabasizsolt.mova.domain.usecase.auth

import android.content.Intent
import com.barabasizsolt.mova.auth.api.AndroidAuthenticationService
import kotlinx.coroutines.flow.filterNotNull

class LoginWithGoogleAccountUseCase(private val service: AndroidAuthenticationService) {

    operator fun invoke(intent: Intent) = service.loginWithGoogleAccount(intent = intent).filterNotNull()
}
