package com.barabasizsolt.mova.auth.implementation

import com.barabasizsolt.mova.auth.api.AuthenticationService
import org.koin.core.module.Module
import org.koin.dsl.module

expect fun createAuthenticationModule(): List<Module>

val commonModule: Module = module { single<AuthenticationService> { AuthenticationServiceImpl() } }