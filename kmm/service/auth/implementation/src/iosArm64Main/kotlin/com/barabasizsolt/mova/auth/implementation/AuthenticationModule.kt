package com.barabasizsolt.mova.auth.implementation

import com.barabasizsolt.mova.auth.api.IosArm64MainAuthenticationService
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun createAuthenticationModule(): List<Module> = buildList {
    add(element = module { single<IosArm64MainAuthenticationService>{ IosArm64MainAuthenticationServiceImpl() } })
    add(element = commonModule)
}