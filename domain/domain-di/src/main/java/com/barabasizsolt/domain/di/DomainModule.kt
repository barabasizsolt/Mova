package com.barabasizsolt.domain.di

import com.barabasizsolt.discover.di.createExploreModule
import com.barabasizsolt.domain.usecase.auth.GetIntentForGoogleAccountLoginUseCase
import com.barabasizsolt.domain.usecase.auth.IsLoggedInUseCase
import com.barabasizsolt.domain.usecase.auth.LogOutUseCase
import com.barabasizsolt.domain.usecase.auth.LoginWithEmailAndPasswordUseCase
import com.barabasizsolt.domain.usecase.auth.LoginWithFacebookAccountUseCase
import com.barabasizsolt.domain.usecase.auth.LoginWithGoogleAccountUseCase
import com.barabasizsolt.domain.usecase.auth.RegisterWithEmailAndPasswordUseCase
import com.barabasizsolt.domain.usecase.auth.ResetPasswordUseCase
import com.barabasizsolt.domain.usecase.screen.explore.movie.DeleteSearchMovieUseCase
import com.barabasizsolt.domain.usecase.screen.explore.movie.DiscoverMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.screen.explore.movie.DiscoverMoviesUseCase
import com.barabasizsolt.domain.usecase.helper.discover.tv.DeleteSearchTvSeriesUseCase
import com.barabasizsolt.domain.usecase.screen.explore.tv.DiscoverTvSeriesFlowUseCase
import com.barabasizsolt.domain.usecase.screen.explore.tv.DiscoverTvSeriesUseCase
import com.barabasizsolt.domain.usecase.helper.movie.nowPlaying.DeleteNowPlayingMoviesUseCase
import com.barabasizsolt.domain.usecase.helper.movie.nowPlaying.GetNowPlayingMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.movie.nowPlaying.GetNowPlayingMoviesUseCase
import com.barabasizsolt.domain.usecase.helper.movie.topRated.DeleteTopRatedMoviesUseCase
import com.barabasizsolt.domain.usecase.helper.movie.topRated.GetTopRatedMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.movie.topRated.GetTopRatedMoviesUseCase
import com.barabasizsolt.domain.usecase.helper.movie.trending.DeletePopularMoviesUseCase
import com.barabasizsolt.domain.usecase.helper.movie.trending.GetPopularMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.movie.trending.GetPopularMoviesUseCase
import com.barabasizsolt.domain.usecase.helper.movie.upcoming.DeleteUpcomingMoviesUseCase
import com.barabasizsolt.domain.usecase.helper.movie.upcoming.GetUpcomingMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.movie.upcoming.GetUpcomingMoviesUseCase
import com.barabasizsolt.domain.usecase.helper.people.DeletePopularPeopleUseCase
import com.barabasizsolt.domain.usecase.helper.people.GetPopularPeopleFlowUseCase
import com.barabasizsolt.domain.usecase.helper.people.GetPopularPeopleUseCase
import com.barabasizsolt.domain.usecase.screen.explore.movie.SearchMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.screen.explore.movie.SearchMoviesUseCase
import com.barabasizsolt.domain.usecase.screen.explore.tv.SearchTvSeriesFlowUseCase
import com.barabasizsolt.domain.usecase.screen.explore.tv.SearchTvSeriesUseCase
import com.barabasizsolt.domain.usecase.screen.home.GetHomeScreenFlowUseCase
import com.barabasizsolt.domain.usecase.screen.home.GetHomeScreenUseCase
import com.barabasizsolt.domain.usecase.screen.seeall.GetSeeAllScreenFlowUseCase
import com.barabasizsolt.domain.usecase.screen.seeall.GetSeeAllScreenUseCase
import com.barabasizsolt.firebase.di.createAuthenticationModule

import com.barabasizsolt.movie.di.createMovieModule
import com.barabasizsolt.people.di.createPeopleModule
import org.koin.dsl.module

fun createDomainModules() = buildList {
    addAll(createServiceModules())
    add(createUseCaseModules())
}

private fun createServiceModules() = buildList {
    add(createExploreModule())
    add(createMovieModule())
    add(createPeopleModule())
    add(createAuthenticationModule())
}

private fun createUseCaseModules() = module {
    // Auth
    factory { GetIntentForGoogleAccountLoginUseCase(service = get()) }
    factory { IsLoggedInUseCase(service = get()) }
    factory { LoginWithEmailAndPasswordUseCase(service = get()) }
    factory { LoginWithGoogleAccountUseCase(service = get()) }
    factory { LoginWithFacebookAccountUseCase(service = get()) }
    factory { LogOutUseCase(service = get()) }
    factory { RegisterWithEmailAndPasswordUseCase(service = get()) }
    factory { ResetPasswordUseCase(service = get()) }

    // Movie [Popular]
    factory { GetPopularMoviesUseCase(movieService = get()) }
    factory { GetPopularMoviesFlowUseCase(movieService = get()) }
    factory { DeletePopularMoviesUseCase(movieService = get()) }

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

    // People [Popular]
    factory { GetPopularPeopleUseCase(peopleService = get()) }
    factory { GetPopularPeopleFlowUseCase(peopleService = get()) }
    factory { DeletePopularPeopleUseCase(peopleService = get()) }

    // Home
    factory {
        GetHomeScreenUseCase(
            getPopularMoviesUseCase = get(),
            getUpcomingMoviesUseCase = get(),
            getTopRatedMoviesUseCase = get(),
            getNowPlayingMoviesCase = get(),
            getPopularPeopleUseCase = get()
        )
    }
    factory {
        GetHomeScreenFlowUseCase(
            getPopularMoviesFlowUseCase = get(),
            getUpcomingMoviesFlowUseCase = get(),
            getTopRatedMoviesFlowUseCase = get(),
            getNowPlayingMoviesFlowCase = get(),
            getPopularPeopleFlowUseCase = get()
        )
    }

    // Explore
    // [Movie]
    factory { DiscoverMoviesUseCase(discoverService = get()) }
    factory { DiscoverMoviesFlowUseCase(discoverService = get()) }
    factory { SearchMoviesUseCase(discoverService = get()) }
    factory { SearchMoviesFlowUseCase(discoverService = get()) }
    factory { DeleteSearchMovieUseCase(discoverService = get()) }

    // [TvSeries]
    factory { DiscoverTvSeriesUseCase(discoverService = get()) }
    factory { DiscoverTvSeriesFlowUseCase(discoverService = get()) }
    factory { SearchTvSeriesUseCase(discoverService = get()) }
    factory { SearchTvSeriesFlowUseCase(discoverService = get()) }
    factory { DeleteSearchTvSeriesUseCase(discoverService = get()) }


    // See All
    factory {
        GetSeeAllScreenUseCase(
            getPopularMoviesUseCase = get(),
            getTopRatedMoviesUseCase = get(),
            getNowPlayingMoviesCase = get(),
            getPopularPeopleUseCase = get()
        )
    }
    factory {
        GetSeeAllScreenFlowUseCase(
            getPopularMoviesFlowUseCase = get(),
            getTopRatedMoviesFlowUseCase = get(),
            getNowPlayingMoviesFlowCase = get(),
            getPopularPeopleFlowUseCase = get()
        )
    }
}