package com.barabasizsolt.mova.domain.di

import com.barabasizsolt.cast.crew.implementation.createCastCrewModule
import com.barabasizsolt.mova.detail.implementation.createDetailModule
import com.barabasizsolt.mova.domain.usecase.helper.castcrew.GetCastCrewUseCase
import com.barabasizsolt.mova.domain.usecase.helper.detail.GetMovieDetailUseCase
import com.barabasizsolt.mova.domain.usecase.helper.genre.GetGenresFlowUseCase
import com.barabasizsolt.mova.domain.usecase.helper.genre.GetGenresUseCase
import com.barabasizsolt.mova.domain.usecase.helper.movie.search.DeleteSearchMovieUseCase
import com.barabasizsolt.mova.domain.usecase.helper.movie.discover.DiscoverMoviesFlowUseCase
import com.barabasizsolt.mova.domain.usecase.helper.movie.discover.DiscoverMoviesUseCase
import com.barabasizsolt.mova.domain.usecase.helper.tvSeries.search.DeleteSearchTvSeriesUseCase
import com.barabasizsolt.mova.domain.usecase.helper.tvSeries.discover.DiscoverTvSeriesFlowUseCase
import com.barabasizsolt.mova.domain.usecase.helper.tvSeries.discover.DiscoverTvSeriesUseCase
import com.barabasizsolt.mova.domain.usecase.helper.movie.nowPlaying.DeleteNowPlayingMoviesUseCase
import com.barabasizsolt.mova.domain.usecase.helper.movie.nowPlaying.GetNowPlayingMoviesFlowUseCase
import com.barabasizsolt.mova.domain.usecase.helper.movie.nowPlaying.GetNowPlayingMoviesUseCase
import com.barabasizsolt.mova.domain.usecase.helper.movie.topRated.DeleteTopRatedMoviesUseCase
import com.barabasizsolt.mova.domain.usecase.helper.movie.topRated.GetTopRatedMoviesFlowUseCase
import com.barabasizsolt.mova.domain.usecase.helper.movie.topRated.GetTopRatedMoviesUseCase
import com.barabasizsolt.mova.domain.usecase.helper.movie.trending.DeletePopularMoviesUseCase
import com.barabasizsolt.mova.domain.usecase.helper.movie.trending.GetPopularMoviesFlowUseCase
import com.barabasizsolt.mova.domain.usecase.helper.movie.trending.GetPopularMoviesUseCase
import com.barabasizsolt.mova.domain.usecase.helper.movie.upcoming.DeleteUpcomingMoviesUseCase
import com.barabasizsolt.mova.domain.usecase.helper.movie.upcoming.GetUpcomingMoviesFlowUseCase
import com.barabasizsolt.mova.domain.usecase.helper.movie.upcoming.GetUpcomingMoviesUseCase
import com.barabasizsolt.mova.domain.usecase.helper.people.DeletePopularPeopleUseCase
import com.barabasizsolt.mova.domain.usecase.helper.people.GetPopularPeopleFlowUseCase
import com.barabasizsolt.mova.domain.usecase.helper.people.GetPopularPeopleUseCase
import com.barabasizsolt.mova.domain.usecase.helper.movie.search.SearchMoviesFlowUseCase
import com.barabasizsolt.mova.domain.usecase.helper.movie.search.SearchMoviesUseCase
import com.barabasizsolt.mova.domain.usecase.helper.movie.similar.DeleteSimilarMoviesUseCase
import com.barabasizsolt.mova.domain.usecase.helper.movie.similar.GetSimilarMoviesFlowUseCase
import com.barabasizsolt.mova.domain.usecase.helper.movie.similar.GetSimilarMoviesUseCase
import com.barabasizsolt.mova.domain.usecase.helper.review.DeleteReviewsUseCase
import com.barabasizsolt.mova.domain.usecase.helper.review.GetReviewsFlowUseCase
import com.barabasizsolt.mova.domain.usecase.helper.review.GetReviewsUseCase
import com.barabasizsolt.mova.domain.usecase.helper.tvSeries.search.SearchTvSeriesFlowUseCase
import com.barabasizsolt.mova.domain.usecase.helper.tvSeries.search.SearchTvSeriesUseCase
import com.barabasizsolt.mova.domain.usecase.helper.video.GetVideosUseCase
import com.barabasizsolt.mova.domain.usecase.screen.detail.GetMovieDetailsUseCase
import com.barabasizsolt.mova.domain.usecase.screen.explore.discover.DiscoverContentFlowUseCase
import com.barabasizsolt.mova.domain.usecase.screen.explore.discover.DiscoverContentUseCase
import com.barabasizsolt.mova.domain.usecase.screen.explore.search.DeleteContentUseCase
import com.barabasizsolt.mova.domain.usecase.screen.explore.search.SearchContentFlowUseCase
import com.barabasizsolt.mova.domain.usecase.screen.explore.search.SearchContentUseCase
import com.barabasizsolt.mova.domain.usecase.screen.home.GetHomeScreenFlowUseCase
import com.barabasizsolt.mova.domain.usecase.screen.home.GetHomeScreenUseCase
import com.barabasizsolt.mova.domain.usecase.screen.seeall.GetSeeAllScreenFlowUseCase
import com.barabasizsolt.mova.domain.usecase.screen.seeall.GetSeeAllScreenUseCase
import com.barabasizsolt.mova.explore.implementation.createExploreModule
import com.barabasizsolt.mova.filter.implementation.createFilterModule
import com.barabasizsolt.mova.genre.implementation.createGenreModule
import com.barabasizsolt.mova.movie.implementation.createMovieModule
import com.barabasizsolt.mova.people.implementation.createPeopleModule
import com.barabasizsolt.mova.review.implementation.createReviewModule
import com.barabasizsolt.mova.video.implementation.createVideoModule
import org.koin.core.module.Module
import org.koin.dsl.module

expect fun createDomainModules(): List<Module>

fun createCommonDomainModules() = buildList {
    addAll(createServiceModules())
    add(createUseCaseModules())
}

private fun createServiceModules() = buildList {
    add(createExploreModule())
    add(createMovieModule())
    add(createPeopleModule())
    add(createGenreModule())
    add(createFilterModule())
    add(createVideoModule())
    add(createReviewModule())
    add(createCastCrewModule())
    add(createDetailModule())
}

private fun createUseCaseModules() = module {
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

    // Movie [Similar]
    factory { GetSimilarMoviesUseCase(movieService = get()) }
    factory { GetSimilarMoviesFlowUseCase(movieService = get()) }
    factory { DeleteSimilarMoviesUseCase(movieService = get()) }

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

    // Video
    factory { GetVideosUseCase(videoService = get()) }

    // Review
    factory { GetReviewsUseCase(reviewService = get()) }
    factory { GetReviewsFlowUseCase(reviewService = get()) }
    factory { DeleteReviewsUseCase(reviewService = get()) }

    // Cast & Crew
    factory { GetCastCrewUseCase(castCrewService = get()) }

    // Movie detail
    factory { GetMovieDetailUseCase(detailService = get()) }

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

    // Detail [Movie]
    factory {
        GetMovieDetailsUseCase(
            getMovieDetailUseCase = get(),
            getVideosUseCase = get(),
            getSimilarMoviesUseCase = get(),
            getReviewsUseCase = get(),
            getCastCrewUseCase = get()
        )
    }
}
