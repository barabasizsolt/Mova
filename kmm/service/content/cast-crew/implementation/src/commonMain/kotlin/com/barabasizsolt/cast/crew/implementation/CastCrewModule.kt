package com.barabasizsolt.cast.crew.implementation

import com.barabasizsolt.cast.crew.api.CastCrewService
import org.koin.dsl.module

fun createCastCrewModule() = module {
    factory { CastCrewRemoteSource(baseHttpClient = get()) }
    single<CastCrewService> { CastCrewServiceImpl(remoteSource = get()) }
}