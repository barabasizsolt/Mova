package com.barabasizsolt.auth

import com.halcyonmobile.oauth.dependencies.SessionExpiredEventHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class SessionExpiredEventHandlerImpl : SessionExpiredEventHandler {

    val sessionExpirationFlow: Flow<Boolean> get() = _sessionExpirationFlow
    private val _sessionExpirationFlow = MutableStateFlow(false)

    override fun onSessionExpired() {
        _sessionExpirationFlow.value = true
    }
}