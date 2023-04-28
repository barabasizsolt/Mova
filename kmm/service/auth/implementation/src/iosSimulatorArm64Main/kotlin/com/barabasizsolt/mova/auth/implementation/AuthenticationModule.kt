package com.barabasizsolt.mova.auth.implementation

import com.barabasizsolt.mova.auth.api.IosSimulatorArm64MainAuthenticationService
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun createAuthenticationModule(): List<Module> = buildList {
    add(element = module { single<IosSimulatorArm64MainAuthenticationService>{ IosSimulatorArm64MainAuthenticationServiceImpl() } })
    add(element = commonModule)
}