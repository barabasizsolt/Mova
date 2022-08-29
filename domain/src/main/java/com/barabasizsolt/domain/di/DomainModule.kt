package com.barabasizsolt.domain.di

import com.barabasizsolt.discover.di.createDiscoverModule
import com.barabasizsolt.domain.useCase.helper.discover.movie.DeleteMovieDiscoverUseCase
import com.barabasizsolt.domain.useCase.helper.discover.movie.GetMovieDiscoverFlowUseCase
import com.barabasizsolt.domain.useCase.helper.discover.movie.GetMovieDiscoverUseCase
import com.barabasizsolt.domain.useCase.helper.movie.trending.DeleteTrendingMoviesUseCase
import com.barabasizsolt.domain.useCase.helper.movie.trending.GetTrendingMoviesFlowUseCase
import com.barabasizsolt.domain.useCase.helper.movie.trending.GetTrendingMoviesUseCase
import com.barabasizsolt.domain.useCase.helper.movie.upcoming.DeleteUpcomingMoviesUseCase
import com.barabasizsolt.domain.useCase.helper.movie.upcoming.GetUpcomingMoviesFlowUseCase
import com.barabasizsolt.domain.useCase.helper.movie.upcoming.GetUpcomingMoviesUseCase
import com.barabasizsolt.domain.useCase.screen.home.GetHomeScreenFlowUseCase
import com.barabasizsolt.domain.useCase.screen.home.GetHomeScreenUseCase
import com.barabasizsolt.movie.di.createMovieModule
import org.koin.dsl.module

fun createDomainModules() = buildList {
    addAll(createServiceModules())
    add(createUseCaseModules())
}

private fun createServiceModules() = buildList {
    add(createDiscoverModule())
    add(createMovieModule())
}

private fun createUseCaseModules() = module {
    // Discover [Movie]
    factory { GetMovieDiscoverUseCase(discoverService = get()) }
    factory { GetMovieDiscoverFlowUseCase(discoverService = get()) }
    factory { DeleteMovieDiscoverUseCase(discoverService = get()) }

    // Movie [Trending]
    factory { GetTrendingMoviesUseCase(movieService = get()) }
    factory { GetTrendingMoviesFlowUseCase(movieService = get()) }
    factory { DeleteTrendingMoviesUseCase(movieService = get()) }

    // Movie [Upcoming]
    factory { GetUpcomingMoviesUseCase(movieService = get()) }
    factory { GetUpcomingMoviesFlowUseCase(movieService = get()) }
    factory { DeleteUpcomingMoviesUseCase(movieService = get()) }

    // Home
    factory {
        GetHomeScreenUseCase(
            getTrendingMoviesUseCase = get(),
            getUpcomingMoviesUseCase = get()
        )
    }
    factory {
        GetHomeScreenFlowUseCase(
            getTrendingMoviesFlowUseCase = get(),
            getUpcomingMoviesFlowUseCase = get()
        )
    }
}