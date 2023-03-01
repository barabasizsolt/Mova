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
import com.barabasizsolt.domain.usecase.helper.genre.GetGenresFlowUseCase
import com.barabasizsolt.domain.usecase.helper.genre.GetGenresUseCase
import com.barabasizsolt.domain.usecase.helper.movie.search.DeleteSearchMovieUseCase
import com.barabasizsolt.domain.usecase.helper.movie.discover.DiscoverMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.movie.discover.DiscoverMoviesUseCase
import com.barabasizsolt.domain.usecase.helper.tvSeries.search.DeleteSearchTvSeriesUseCase
import com.barabasizsolt.domain.usecase.helper.tvSeries.discover.DiscoverTvSeriesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.tvSeries.discover.DiscoverTvSeriesUseCase
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
import com.barabasizsolt.domain.usecase.helper.movie.search.SearchMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.movie.search.SearchMoviesUseCase
import com.barabasizsolt.domain.usecase.helper.tvSeries.search.SearchTvSeriesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.tvSeries.search.SearchTvSeriesUseCase
import com.barabasizsolt.domain.usecase.screen.explore.discover.DiscoverContentFlowUseCase
import com.barabasizsolt.domain.usecase.screen.explore.discover.DiscoverContentUseCase
import com.barabasizsolt.domain.usecase.screen.explore.search.DeleteContentUseCase
import com.barabasizsolt.domain.usecase.screen.explore.search.SearchContentFlowUseCase
import com.barabasizsolt.domain.usecase.screen.explore.search.SearchContentUseCase
import com.barabasizsolt.domain.usecase.screen.home.GetHomeScreenFlowUseCase
import com.barabasizsolt.domain.usecase.screen.home.GetHomeScreenUseCase
import com.barabasizsolt.domain.usecase.screen.seeall.GetSeeAllScreenFlowUseCase
import com.barabasizsolt.domain.usecase.screen.seeall.GetSeeAllScreenUseCase
import com.barabasizsolt.firebase.di.createAuthenticationModule
import com.barabasizsolt.genre.di.createGenreModule

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
    add(createGenreModule())
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

    // Genre
    factory { GetGenresUseCase(genreService = get()) }
    factory { GetGenresFlowUseCase(genreService = get()) }

    // Discover [Movie]
    factory { DiscoverMoviesUseCase(exploreService = get()) }
    factory { DiscoverMoviesFlowUseCase(exploreService = get()) }

    // Search [Movie]
    factory { SearchMoviesUseCase(exploreService = get()) }
    factory { SearchMoviesFlowUseCase(exploreService = get()) }
    factory { DeleteSearchMovieUseCase(exploreService = get()) }

    // Discover [TvSeries]
    factory { DiscoverTvSeriesUseCase(exploreService = get()) }
    factory { DiscoverTvSeriesFlowUseCase(exploreService = get()) }

    // Search [TvSeries]
    factory { SearchTvSeriesUseCase(exploreService = get()) }
    factory { SearchTvSeriesFlowUseCase(exploreService = get()) }
    factory { DeleteSearchTvSeriesUseCase(exploreService = get()) }

    // Home
    factory {
        GetHomeScreenUseCase(
            getPopularMoviesUseCase = get(),
            getUpcomingMoviesUseCase = get(),
            getTopRatedMoviesUseCase = get(),
            getNowPlayingMoviesCase = get(),
            getPopularPeopleUseCase = get(),
            getGenresUseCase = get()
        )
    }
    factory {
        GetHomeScreenFlowUseCase(
            getPopularMoviesFlowUseCase = get(),
            getUpcomingMoviesFlowUseCase = get(),
            getTopRatedMoviesFlowUseCase = get(),
            getNowPlayingMoviesFlowCase = get(),
            getPopularPeopleFlowUseCase = get(),
            getGenresFlowUseCase = get()
        )
    }

    // Explore
    factory {
        DiscoverContentUseCase(
            discoverMoviesUseCase = get(),
            discoverTvSeriesUseCase = get()
        )
    }
    factory {
        DiscoverContentFlowUseCase(
            discoverMoviesFlowUseCase = get(),
            discoverTvSeriesFlowUseCase = get()
        )
    }
    factory {
        SearchContentUseCase(
            searchMoviesUseCase = get(),
            searchTvSeriesUseCase = get()
        )
    }
    factory {
        SearchContentFlowUseCase(
            searchMoviesFlowUseCase = get(),
            searchTvSeriesFlowUseCase = get()
        )
    }
    factory {
        DeleteContentUseCase(
            deleteSearchMovieUseCase = get(),
            deleteSearchTvSeriesUseCase = get()
        )
    }

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