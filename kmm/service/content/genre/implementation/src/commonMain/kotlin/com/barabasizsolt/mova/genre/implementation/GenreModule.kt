package com.barabasizsolt.genre.di

import com.barabasizsolt.api.RetrofitClient
import com.barabasizsolt.genre.api.GenreService
import com.barabasizsolt.genre.implementation.GenreNetworkService
import com.barabasizsolt.genre.implementation.GenreRemoteSource
import com.barabasizsolt.genre.implementation.GenreServiceImpl
import org.koin.dsl.module

fun createGenreModule() = module {
    factory { get<RetrofitClient>().sessionless.create(GenreNetworkService::class.java) }
    factory { GenreRemoteSource(networkService = get()) }
    single<GenreService> { GenreServiceImpl(remoteSource = get()) }
}