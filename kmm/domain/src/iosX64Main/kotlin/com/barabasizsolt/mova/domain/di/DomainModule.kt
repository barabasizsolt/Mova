package com.barabasizsolt.mova.domain.di

import com.barabasizsolt.mova.auth.api.IosX64MainAuthenticationService
import com.barabasizsolt.mova.auth.implementation.createAuthenticationModule
import com.barabasizsolt.mova.domain.usecase.auth.IsLoggedInUseCase
import com.barabasizsolt.mova.domain.usecase.auth.LogOutUseCase
import com.barabasizsolt.mova.domain.usecase.auth.LoginWithEmailAndPasswordUseCase
import com.barabasizsolt.mova.domain.usecase.auth.RegisterWithEmailAndPasswordUseCase
import com.barabasizsolt.mova.domain.usecase.auth.ResetPasswordUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun createDomainModules(): List<Module> = buildList {
    addAll(createAuthenticationModule())
    add(createAuthUseCaseModule())
} + createCommonDomainModules()

private fun createAuthUseCaseModule() = module {
    // Auth
    factory { IsLoggedInUseCase(service = get() as IosX64MainAuthenticationService) }
    factory { LoginWithEmailAndPasswordUseCase(service = get() as IosX64MainAuthenticationService) }
    factory { LogOutUseCase(service = get() as IosX64MainAuthenticationService) }
    factory { RegisterWithEmailAndPasswordUseCase(service = get() as IosX64MainAuthenticationService) }
    factory { ResetPasswordUseCase(service = get() as IosX64MainAuthenticationService) }
}