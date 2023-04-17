package com.barabasizsolt.mova.explore.implementation

import com.barabasizsolt.mova.explore.api.ExploreService
import org.koin.dsl.module

fun createExploreModule() = module {
    factory { ExploreRemoteSource(baseHttpClient = get()) }
    single<ExploreService> { ExploreServiceImpl(remoteSource = get()) }
}

