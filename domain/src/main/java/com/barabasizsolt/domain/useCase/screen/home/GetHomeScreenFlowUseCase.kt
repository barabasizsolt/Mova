package com.barabasizsolt.domain.useCase.screen.home

import com.barabasizsolt.domain.model.HomeScreenContent
import com.barabasizsolt.domain.model.toWatchableItem
import com.barabasizsolt.domain.useCase.helper.movie.nowPlaying.GetNowPlayingMoviesFlowUseCase
import com.barabasizsolt.domain.useCase.helper.movie.topRated.GetTopRatedMoviesFlowUseCase
import com.barabasizsolt.domain.useCase.helper.movie.trending.GetTrendingMoviesFlowUseCase
import com.barabasizsolt.domain.useCase.helper.movie.upcoming.GetUpcomingMoviesFlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetHomeScreenFlowUseCase(
    private val getTrendingMoviesFlowUseCase: GetTrendingMoviesFlowUseCase,
    private val getUpcomingMoviesFlowUseCase: GetUpcomingMoviesFlowUseCase,
    private val getTopRatedMoviesFlowUseCase: GetTopRatedMoviesFlowUseCase,
    private val getNowPlayingMoviesFlowCase: GetNowPlayingMoviesFlowUseCase
) {

    operator fun invoke(): Flow<HomeScreenContent> = combine(
        getTrendingMoviesFlowUseCase(),
        getUpcomingMoviesFlowUseCase(),
        getTopRatedMoviesFlowUseCase(),
        getNowPlayingMoviesFlowCase()
    ) { trending ,upcoming, topRated, nowPlaying ->
        HomeScreenContent(
            trendingMovies = trending.results.map { it.toWatchableItem() },
            upcomingMovies = upcoming.results.map { it.toWatchableItem() },
            topRatedMovies = topRated.results.map { it.toWatchableItem() },
            nowPlayingMovies = nowPlaying.results
        )
    }
}