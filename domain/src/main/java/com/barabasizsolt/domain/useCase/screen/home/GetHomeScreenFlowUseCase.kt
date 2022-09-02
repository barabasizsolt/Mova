package com.barabasizsolt.domain.useCase.screen.home

import com.barabasizsolt.domain.model.HomeScreenContent
import com.barabasizsolt.domain.model.toWatchableItem
import com.barabasizsolt.domain.useCase.helper.movie.nowPlaying.GetNowPlayingMoviesFlowUseCase
import com.barabasizsolt.domain.useCase.helper.movie.topRated.GetTopRatedMoviesFlowUseCase
import com.barabasizsolt.domain.useCase.helper.movie.trending.GetTrendingMoviesFlowUseCase
import com.barabasizsolt.domain.useCase.helper.movie.upcoming.GetUpcomingMoviesFlowUseCase
import com.barabasizsolt.domain.useCase.helper.people.GetPopularPeopleFlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetHomeScreenFlowUseCase(
    private val getTrendingMoviesFlowUseCase: GetTrendingMoviesFlowUseCase,
    private val getUpcomingMoviesFlowUseCase: GetUpcomingMoviesFlowUseCase,
    private val getTopRatedMoviesFlowUseCase: GetTopRatedMoviesFlowUseCase,
    private val getNowPlayingMoviesFlowCase: GetNowPlayingMoviesFlowUseCase,
    private val getPopularPeopleFlowUseCase: GetPopularPeopleFlowUseCase
) {

    operator fun invoke(): Flow<HomeScreenContent> = combine(
        getTrendingMoviesFlowUseCase(),
        getUpcomingMoviesFlowUseCase(),
        getTopRatedMoviesFlowUseCase(),
        getNowPlayingMoviesFlowCase(),
        getPopularPeopleFlowUseCase()
    ) { trending ,upcoming, topRated, nowPlaying, popularPeople ->
        HomeScreenContent(
            trendingMovies = trending.results.map { it.toWatchableItem() },
            upcomingMovies = upcoming.results.map { it.toWatchableItem() },
            topRatedMovies = topRated.results.map { it.toWatchableItem() },
            nowPlayingMovies = nowPlaying.results,
            popularPeople = popularPeople.results.map { it.toWatchableItem() }
        )
    }
}