package com.barabasizsolt.movie.di

import com.barabasizsolt.api.RetrofitClient
import com.barabasizsolt.movie.api.MovieService
import com.barabasizsolt.movie.implementation.MovieRemoteSource
import com.barabasizsolt.movie.implementation.MovieServiceImpl
import org.koin.dsl.module

fun createMovieModule() = module {
    //factory { get<RetrofitClient>().sessionless.create(MovieNetworkService::class.java) }
    factory { MovieRemoteSource(httpClient = get()) }
    single<MovieService> { MovieServiceImpl(remoteSource = get()) }
}