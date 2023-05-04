package com.barabasizsolt.mova.domain.usecase.auth

import com.barabasizsolt.mova.auth.api.AndroidAuthenticationService

class GetIntentForGoogleAccountLoginUseCase(private val service: AndroidAuthenticationService) {

    operator fun invoke() = service.getIntentForGoogleAccountLogin()
}
