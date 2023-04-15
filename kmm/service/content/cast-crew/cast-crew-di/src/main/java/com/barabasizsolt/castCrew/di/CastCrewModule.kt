package com.barabasizsolt.castCrew.di

import com.barabasizsolt.api.RetrofitClient
import com.barabasizsolt.castCrew.api.CastCrewService
import com.barabasizsolt.castCrew.implementation.CastCrewNetworkService
import com.barabasizsolt.castCrew.implementation.CastCrewRemoteSource
import com.barabasizsolt.castCrew.implementation.CastCrewServiceImpl
import org.koin.dsl.module

fun createCastCrewModule() = module {
    factory { get<RetrofitClient>().sessionless.create(CastCrewNetworkService::class.java) }
    factory { CastCrewRemoteSource(networkService = get()) }
    single<CastCrewService> { CastCrewServiceImpl(remoteSource = get()) }
}