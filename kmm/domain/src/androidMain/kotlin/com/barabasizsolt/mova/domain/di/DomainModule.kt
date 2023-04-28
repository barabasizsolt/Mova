@file:JvmName("AndroidDomainModule")

package com.barabasizsolt.mova.domain.di

import com.barabasizsolt.mova.auth.api.AndroidAuthenticationService
import com.barabasizsolt.mova.auth.implementation.createAuthenticationModule
import com.barabasizsolt.mova.domain.usecase.auth.GetIntentForGoogleAccountLoginUseCase
import com.barabasizsolt.mova.domain.usecase.auth.IsLoggedInUseCase
import com.barabasizsolt.mova.domain.usecase.auth.LogOutUseCase
import com.barabasizsolt.mova.domain.usecase.auth.LoginWithEmailAndPasswordUseCase
import com.barabasizsolt.mova.domain.usecase.auth.LoginWithFacebookAccountUseCase
import com.barabasizsolt.mova.domain.usecase.auth.LoginWithGoogleAccountUseCase
import com.barabasizsolt.mova.domain.usecase.auth.RegisterWithEmailAndPasswordUseCase
import com.barabasizsolt.mova.domain.usecase.auth.ResetPasswordUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun createDomainModules() : List<Module> = buildList {
    addAll(createAuthenticationModule())
    add(createAuthUseCaseModule())
} + createCommonDomainModules()

private fun createAuthUseCaseModule() = module {
    // Auth
    factory { GetIntentForGoogleAccountLoginUseCase(service = get() as AndroidAuthenticationService) }
    factory { IsLoggedInUseCase(service = get() as AndroidAuthenticationService) }
    factory { LoginWithEmailAndPasswordUseCase(service = get() as AndroidAuthenticationService) }
    factory { LoginWithGoogleAccountUseCase(service = get() as AndroidAuthenticationService) }
    factory { LoginWithFacebookAccountUseCase(service = get() as AndroidAuthenticationService) }
    factory { LogOutUseCase(service = get() as AndroidAuthenticationService) }
    factory { RegisterWithEmailAndPasswordUseCase(service = get() as AndroidAuthenticationService) }
    factory { ResetPasswordUseCase(service = get() as AndroidAuthenticationService) }
}