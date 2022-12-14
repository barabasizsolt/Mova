package com.barabasizsolt.firebase.di

import com.barabasizsolt.api.AuthenticationService
import com.barabasizsolt.firebase.implementation.AuthenticationServiceImpl
import org.koin.dsl.module

fun createAuthenticationModule() = module {
    single<AuthenticationService>{ AuthenticationServiceImpl(activityProvider = get()) }
}
