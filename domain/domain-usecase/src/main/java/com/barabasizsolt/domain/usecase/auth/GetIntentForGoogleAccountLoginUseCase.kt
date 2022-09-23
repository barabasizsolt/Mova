package com.barabasizsolt.domain.usecase.auth

import com.barabasizsolt.api.AuthenticationService

class GetIntentForGoogleAccountLoginUseCase(private val service: AuthenticationService) {

    operator fun invoke() = service.getIntentForGoogleAccountLogin()
}
