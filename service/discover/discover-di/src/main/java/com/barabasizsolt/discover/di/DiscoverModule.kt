package com.barabasizsolt.discover.di

import com.barabasizsolt.api.RetrofitClient
import com.barabasizsolt.discover.api.DiscoverService
import com.barabasizsolt.discover.implementation.DiscoverNetworkService
import com.barabasizsolt.discover.implementation.DiscoverRemoteSource
import com.barabasizsolt.discover.implementation.DiscoverServiceImpl
import org.koin.dsl.module

fun createDiscoverModule() = module {
    factory { get<RetrofitClient>().sessionless.create(DiscoverNetworkService::class.java) }
    factory { DiscoverRemoteSource(networkService = get()) }
    single<DiscoverService> { DiscoverServiceImpl(remoteSource = get()) }
}

