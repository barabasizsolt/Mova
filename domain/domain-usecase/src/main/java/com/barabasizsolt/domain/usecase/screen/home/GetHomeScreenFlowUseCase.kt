package com.barabasizsolt.domain.usecase.screen.home

import com.barabasizsolt.domain.model.ContentItem
import com.barabasizsolt.domain.model.HomeScreenContent
import com.barabasizsolt.domain.model.toContentItem
import com.barabasizsolt.domain.usecase.helper.movie.nowPlaying.GetNowPlayingMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.movie.topRated.GetTopRatedMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.movie.trending.GetPopularMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.movie.upcoming.GetUpcomingMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.people.GetPopularPeopleFlowUseCase
import com.barabasizsolt.movie.model.Movie
import com.barabasizsolt.people.model.People
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetHomeScreenFlowUseCase(
    private val getPopularMoviesFlowUseCase: GetPopularMoviesFlowUseCase,
    private val getUpcomingMoviesFlowUseCase: GetUpcomingMoviesFlowUseCase,
    private val getTopRatedMoviesFlowUseCase: GetTopRatedMoviesFlowUseCase,
    private val getNowPlayingMoviesFlowCase: GetNowPlayingMoviesFlowUseCase,
    private val getPopularPeopleFlowUseCase: GetPopularPeopleFlowUseCase
) {

    operator fun invoke(): Flow<HomeScreenContent> = combine(
        getUpcomingMoviesFlowUseCase(),
        getPopularMoviesFlowUseCase(),
        getTopRatedMoviesFlowUseCase(),
        getNowPlayingMoviesFlowCase(),
        getPopularPeopleFlowUseCase()
    ) { upcoming, popular, topRated, nowPlaying, popularPeople ->
        HomeScreenContent(
            upcomingMovies = upcoming.filterIsInstance<Movie>(),
            popularMovies = popular.filterIsInstance<Movie>().take(n = MAX_ITEM).map { it.toContentItem() as ContentItem.Watchable },
            nowPlayingMovies = nowPlaying.filterIsInstance<Movie>().take(n = MAX_ITEM).map { it.toContentItem() as ContentItem.Watchable },
            topRatedMovies = topRated.filterIsInstance<Movie>().take(n = MAX_ITEM).map { it.toContentItem() as ContentItem.Watchable },
            popularPeople = popularPeople.filterIsInstance<People>().map { it.toContentItem() as ContentItem.Person }
        )
    }

    companion object {
        private const val MAX_ITEM: Int = 20
    }
}