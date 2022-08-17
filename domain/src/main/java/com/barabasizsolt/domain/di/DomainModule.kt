package com.barabasizsolt.domain.di

import com.barabasizsolt.discover.di.createDiscoverModule
import com.barabasizsolt.domain.useCase.discover.movie.DeleteMovieDiscoverUseCase
import com.barabasizsolt.domain.useCase.discover.movie.GetMovieDiscoverFlowUseCase
import com.barabasizsolt.domain.useCase.discover.movie.GetMovieDiscoverUseCase
import org.koin.dsl.module

fun createDomainModules() = buildList {
    addAll(createServiceModules())
    add(createUseCaseModules())
}

private fun createServiceModules() = buildList {
    add(createDiscoverModule())
}

private fun createUseCaseModules() = module {
    factory { GetMovieDiscoverUseCase(discoverService = get()) }
    factory { GetMovieDiscoverFlowUseCase(discoverService = get()) }
    factory { DeleteMovieDiscoverUseCase(discoverService = get()) }
}