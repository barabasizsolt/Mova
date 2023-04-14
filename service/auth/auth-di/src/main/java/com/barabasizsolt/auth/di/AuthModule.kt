package com.barabasizsolt.auth

import com.barabasizsolt.auth.implementation.AuthenticationLocalStorageImpl
import com.barabasizsolt.auth.implementation.SessionExpiredEventHandlerImpl
import com.halcyonmobile.oauth.dependencies.AuthenticationLocalStorage
import com.halcyonmobile.oauth.dependencies.SessionExpiredEventHandler
import org.koin.dsl.module

fun createAuthModules() = buildList {
    add(createAuthenticationStorageModule())
    add(createSessionExpiredHandlerModule())
}

private fun createAuthenticationStorageModule() = module {
    single { AuthenticationLocalStorageImpl(context = get()) }
    single <AuthenticationLocalStorage> { get<AuthenticationLocalStorageImpl>() }
}

private fun createSessionExpiredHandlerModule() = module {
    single<SessionExpiredEventHandler> { get<SessionExpiredEventHandlerImpl>() }
    single { SessionExpiredEventHandlerImpl() }
}