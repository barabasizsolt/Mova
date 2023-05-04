package com.barabasizsolt.mova.people.implementation

import com.barabasizsolt.mova.people.api.PeopleService
import org.koin.dsl.module

fun createPeopleModule() = module {
    factory { PeopleRemoteSource(baseHttpClient = get()) }
    single<PeopleService> { PeopleServiceImpl(remoteSource = get()) }
}