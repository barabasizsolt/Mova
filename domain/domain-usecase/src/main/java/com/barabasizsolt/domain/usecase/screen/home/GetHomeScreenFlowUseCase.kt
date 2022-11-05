package com.barabasizsolt.domain.usecase.screen.home

import com.barabasizsolt.domain.model.HomeScreenContent
import com.barabasizsolt.domain.model.toWatchableItem
import com.barabasizsolt.domain.usecase.helper.movie.nowPlaying.GetNowPlayingMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.movie.topRated.GetTopRatedMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.movie.trending.GetPopularMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.movie.upcoming.GetUpcomingMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.people.GetPopularPeopleFlowUseCase
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
        getPopularMoviesFlowUseCase(),
        getUpcomingMoviesFlowUseCase(),
        getTopRatedMoviesFlowUseCase(),
        getNowPlayingMoviesFlowCase(),
        getPopularPeopleFlowUseCase()
    ) { trending, upcoming, topRated, nowPlaying, popularPeople ->
        HomeScreenContent(
            trendingMovies = trending.take(n = MAX_ITEM).map { it.toWatchableItem() },
            upcomingMovies = upcoming.take(n = MAX_ITEM).map { it.toWatchableItem() },
            topRatedMovies = topRated.take(n = MAX_ITEM).map { it.toWatchableItem() },
            nowPlayingMovies = nowPlaying.take(n = MAX_ITEM),
            popularPeople = popularPeople.results.map { it.toWatchableItem() }
        )
    }

    companion object {
        private const val MAX_ITEM: Int = 20
    }
}