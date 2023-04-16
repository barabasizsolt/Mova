package com.barabasizsolt.mova.movie.implementation

import com.barabasizsolt.mova.api.MovieService
import org.koin.dsl.module

fun createMovieModule() = module {
    factory { MovieRemoteSource(httpClient = get()) }
    single<MovieService> { MovieServiceImpl(remoteSource = get()) }
}