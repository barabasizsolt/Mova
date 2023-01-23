package com.barabasizsolt.discover.di

import com.barabasizsolt.api.RetrofitClient
import com.barabasizsolt.discover.api.ExploreService
import com.barabasizsolt.discover.implementation.ExploreNetworkService
import com.barabasizsolt.discover.implementation.ExploreRemoteSource
import com.barabasizsolt.discover.implementation.ExploreServiceImpl
import org.koin.dsl.module

fun createExploreModule() = module {
    factory { get<RetrofitClient>().sessionless.create(ExploreNetworkService::class.java) }
    factory { ExploreRemoteSource(networkService = get()) }
    single<ExploreService> { ExploreServiceImpl(remoteSource = get(), pager = get()) }
}

