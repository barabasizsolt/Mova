package com.barabasizsolt.domain.di

import com.barabasizsolt.discover.di.createDiscoverModule
import com.barabasizsolt.domain.useCase.helper.discover.movie.DeleteMovieDiscoverUseCase
import com.barabasizsolt.domain.useCase.helper.discover.movie.GetMovieDiscoverFlowUseCase
import com.barabasizsolt.domain.useCase.helper.discover.movie.GetMovieDiscoverUseCase
import com.barabasizsolt.domain.useCase.helper.movie.nowPlaying.DeleteNowPlayingMoviesUseCase
import com.barabasizsolt.domain.useCase.helper.movie.nowPlaying.GetNowPlayingMoviesFlowUseCase
import com.barabasizsolt.domain.useCase.helper.movie.nowPlaying.GetNowPlayingMoviesUseCase
import com.barabasizsolt.domain.useCase.helper.movie.topRated.DeleteTopRatedMoviesUseCase
import com.barabasizsolt.domain.useCase.helper.movie.topRated.GetTopRatedMoviesFlowUseCase
import com.barabasizsolt.domain.useCase.helper.movie.topRated.GetTopRatedMoviesUseCase
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

    // Movie [Top Rated]
    factory { GetTopRatedMoviesUseCase(movieService = get()) }
    factory { GetTopRatedMoviesFlowUseCase(movieService = get()) }
    factory { DeleteTopRatedMoviesUseCase(movieService = get()) }

    // Movie [Now Playing]
    factory { GetNowPlayingMoviesUseCase(movieService = get()) }
    factory { GetNowPlayingMoviesFlowUseCase(movieService = get()) }
    factory { DeleteNowPlayingMoviesUseCase(movieService = get()) }

    // Home
    factory {
        GetHomeScreenUseCase(
            getTrendingMoviesUseCase = get(),
            getUpcomingMoviesUseCase = get(),
            getTopRatedMoviesUseCase = get(),
            getNowPlayingMoviesCase = get()
        )
    }
    factory {
        GetHomeScreenFlowUseCase(
            getTrendingMoviesFlowUseCase = get(),
            getUpcomingMoviesFlowUseCase = get(),
            getTopRatedMoviesFlowUseCase = get(),
            getNowPlayingMoviesFlowCase = get()
        )
    }
}