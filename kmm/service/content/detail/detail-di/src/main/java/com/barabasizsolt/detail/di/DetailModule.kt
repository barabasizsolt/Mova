package com.barabasizsolt.detail.di

import com.barabasizsolt.api.RetrofitClient
import com.barabasizsolt.detail.api.DetailService
import com.barabasizsolt.detail.implementation.DetailNetworkService
import com.barabasizsolt.detail.implementation.DetailRemoteSource
import com.barabasizsolt.detail.implementation.DetailServiceImpl
import org.koin.dsl.module

fun createDetailModule() = module {
    factory { get<RetrofitClient>().sessionless.create(DetailNetworkService::class.java) }
    factory { DetailRemoteSource(networkService = get()) }
    single<DetailService> { DetailServiceImpl(remoteSource = get()) }
}

