package com.barabasizsolt.mova.genre.implementation

import com.barabasizsolt.mova.genre.api.GenreService
import org.koin.dsl.module

fun createGenreModule() = module {
    factory { GenreRemoteSource(baseHttpClient = get()) }
    single<GenreService> { GenreServiceImpl(remoteSource = get()) }
}