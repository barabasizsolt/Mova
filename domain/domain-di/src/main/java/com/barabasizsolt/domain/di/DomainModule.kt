package com.barabasizsolt.domain.di

import com.barabasizsolt.discover.di.createDiscoverModule
import com.barabasizsolt.domain.usecase.auth.GetIntentForGoogleAccountLoginUseCase
import com.barabasizsolt.domain.usecase.auth.IsLoggedInUseCase
import com.barabasizsolt.domain.usecase.auth.LogOutUseCase
import com.barabasizsolt.domain.usecase.auth.LoginWithEmailAndPasswordUseCase
import com.barabasizsolt.domain.usecase.auth.LoginWithGoogleAccountUseCase
import com.barabasizsolt.domain.usecase.auth.RegisterWithEmailAndPasswordUseCase
import com.barabasizsolt.domain.usecase.auth.ResetPasswordUseCase
import com.barabasizsolt.domain.usecase.helper.discover.movie.DeleteMovieDiscoverUseCase
import com.barabasizsolt.domain.usecase.helper.discover.movie.GetMovieDiscoverFlowUseCase
import com.barabasizsolt.domain.usecase.helper.discover.movie.GetMovieDiscoverUseCase
import com.barabasizsolt.domain.usecase.helper.discover.tv.DeleteTvDiscoverUseCase
import com.barabasizsolt.domain.usecase.helper.discover.tv.GetTvDiscoverFlowUseCase
import com.barabasizsolt.domain.usecase.helper.discover.tv.GetTvDiscoverUseCase
import com.barabasizsolt.domain.usecase.helper.movie.nowPlaying.DeleteNowPlayingMoviesUseCase
import com.barabasizsolt.domain.usecase.helper.movie.nowPlaying.GetNowPlayingMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.movie.nowPlaying.GetNowPlayingMoviesUseCase
import com.barabasizsolt.domain.usecase.helper.movie.topRated.DeleteTopRatedMoviesUseCase
import com.barabasizsolt.domain.usecase.helper.movie.topRated.GetTopRatedMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.movie.topRated.GetTopRatedMoviesUseCase
import com.barabasizsolt.domain.usecase.helper.movie.trending.DeleteTrendingMoviesUseCase
import com.barabasizsolt.domain.usecase.helper.movie.trending.GetTrendingMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.movie.trending.GetTrendingMoviesUseCase
import com.barabasizsolt.domain.usecase.helper.movie.upcoming.DeleteUpcomingMoviesUseCase
import com.barabasizsolt.domain.usecase.helper.movie.upcoming.GetUpcomingMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.movie.upcoming.GetUpcomingMoviesUseCase
import com.barabasizsolt.domain.usecase.helper.people.DeletePopularPeopleUseCase
import com.barabasizsolt.domain.usecase.helper.people.GetPopularPeopleFlowUseCase
import com.barabasizsolt.domain.usecase.helper.people.GetPopularPeopleUseCase
import com.barabasizsolt.domain.usecase.screen.explore.GetExploreScreenFlowUseCase
import com.barabasizsolt.domain.usecase.screen.explore.GetExploreScreenUseCase
import com.barabasizsolt.domain.usecase.screen.home.GetHomeScreenFlowUseCase
import com.barabasizsolt.domain.usecase.screen.home.GetHomeScreenUseCase
import com.barabasizsolt.firebase.di.createAuthenticationModule

import com.barabasizsolt.movie.di.createMovieModule
import com.barabasizsolt.people.di.createPeopleModule
import org.koin.dsl.module

fun createDomainModules() = buildList {
    addAll(createServiceModules())
    add(createUseCaseModules())
}

private fun createServiceModules() = buildList {
    add(createDiscoverModule())
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
    factory { LogOutUseCase(service = get()) }
    factory { RegisterWithEmailAndPasswordUseCase(service = get()) }
    factory { ResetPasswordUseCase(service = get()) }

    // Discover [Movie]
    factory { GetMovieDiscoverUseCase(discoverService = get()) }
    factory { GetMovieDiscoverFlowUseCase(discoverService = get()) }
    factory { DeleteMovieDiscoverUseCase(discoverService = get()) }

    // Discover [Tv]
    factory { GetTvDiscoverUseCase(discoverService = get()) }
    factory { GetTvDiscoverFlowUseCase(discoverService = get()) }
    factory { DeleteTvDiscoverUseCase(discoverService = get()) }

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

    // People [Popular]
    factory { GetPopularPeopleUseCase(peopleService = get()) }
    factory { GetPopularPeopleFlowUseCase(peopleService = get()) }
    factory { DeletePopularPeopleUseCase(peopleService = get()) }

    // Home
    factory {
        GetHomeScreenUseCase(
            getTrendingMoviesUseCase = get(),
            getUpcomingMoviesUseCase = get(),
            getTopRatedMoviesUseCase = get(),
            getNowPlayingMoviesCase = get(),
            getPopularPeopleUseCase = get()
        )
    }
    factory {
        GetHomeScreenFlowUseCase(
            getTrendingMoviesFlowUseCase = get(),
            getUpcomingMoviesFlowUseCase = get(),
            getTopRatedMoviesFlowUseCase = get(),
            getNowPlayingMoviesFlowCase = get(),
            getPopularPeopleFlowUseCase = get()
        )
    }

    // Explore
    factory {
        GetExploreScreenUseCase(
            getMovieDiscoverUseCase = get(),
            getTvDiscoverUseCase = get()
        )
    }
    factory {
        GetExploreScreenFlowUseCase(
            getMovieDiscoverFlowUseCase = get(),
            getTvDiscoverFlowUseCase = get()
        )
    }
}