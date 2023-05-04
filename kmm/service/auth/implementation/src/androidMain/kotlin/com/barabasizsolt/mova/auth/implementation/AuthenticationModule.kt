@file:JvmName("AndroidAuthenticationModuleUnique")

package com.barabasizsolt.mova.auth.implementation

import com.barabasizsolt.mova.auth.api.AndroidAuthenticationService
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun createAuthenticationModule(): List<Module> = buildList {
    add(element = module { single<AndroidAuthenticationService>{ AndroidAuthenticationServiceImpl() } })
    add(element = commonModule)
}