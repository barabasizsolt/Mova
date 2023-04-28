package com.barabasizsolt.mova.auth.implementation

import com.barabasizsolt.mova.auth.api.IosX64MainAuthenticationService
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun createAuthenticationModule(): List<Module> = buildList {
    add(element = module { single<IosX64MainAuthenticationService> { IosX64MainAuthenticationServiceImpl() } })
    add(element = commonModule)
}