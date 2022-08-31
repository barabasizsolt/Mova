package com.barabasizsolt.people.di

import com.barabasizsolt.api.RetrofitClient
import com.barabasizsolt.people.api.PeopleService
import com.barabasizsolt.people.implementation.PeopleNetworkService
import com.barabasizsolt.people.implementation.PeopleRemoteSource
import com.barabasizsolt.people.implementation.PeopleServiceImpl
import org.koin.dsl.module

fun createPeopleModule() = module {
    factory { get<RetrofitClient>().sessionless.create(PeopleNetworkService::class.java) }
    factory { PeopleRemoteSource(networkService = get()) }
    single<PeopleService> { PeopleServiceImpl(remoteSource = get()) }
}