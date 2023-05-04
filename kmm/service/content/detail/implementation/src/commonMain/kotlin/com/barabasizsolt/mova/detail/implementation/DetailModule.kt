package com.barabasizsolt.mova.detail.implementation

import com.barabasizsolt.mova.detail.api.DetailService
import org.koin.dsl.module

fun createDetailModule() = module {
    factory { DetailRemoteSource(baseHttpClient = get()) }
    single<DetailService> { DetailServiceImpl(remoteSource = get()) }
}

