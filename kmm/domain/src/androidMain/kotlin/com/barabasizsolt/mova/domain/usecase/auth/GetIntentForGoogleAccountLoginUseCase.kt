package com.barabasizsolt.mova.domain.usecase.auth

import com.barabasizsolt.api.AuthenticationService

class GetIntentForGoogleAccountLoginUseCase(private val service: AuthenticationService) {

    operator fun invoke() = service.getIntentForGoogleAccountLogin()
}
