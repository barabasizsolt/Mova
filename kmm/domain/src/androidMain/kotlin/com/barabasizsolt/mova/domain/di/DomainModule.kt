@file:JvmName("AndroidDomainModule")

package com.barabasizsolt.mova.domain.di

import com.barabasizsolt.firebase.di.createAuthenticationModule
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
    add(createAuthenticationModule())
    add(createAuthUseCaseModule())
} + createCommonDomainModules()

private fun createAuthUseCaseModule() = module {
    // Auth
    factory { GetIntentForGoogleAccountLoginUseCase(service = get()) }
    factory { IsLoggedInUseCase(service = get()) }
    factory { LoginWithEmailAndPasswordUseCase(service = get()) }
    factory { LoginWithGoogleAccountUseCase(service = get()) }
    factory { LoginWithFacebookAccountUseCase(service = get()) }
    factory { LogOutUseCase(service = get()) }
    factory { RegisterWithEmailAndPasswordUseCase(service = get()) }
    factory { ResetPasswordUseCase(service = get()) }
}