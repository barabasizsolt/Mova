package com.barabasizsolt.mova.domain.di

import org.koin.core.module.Module

actual fun createDomainModules(): List<Module> = createCommonDomainModules()