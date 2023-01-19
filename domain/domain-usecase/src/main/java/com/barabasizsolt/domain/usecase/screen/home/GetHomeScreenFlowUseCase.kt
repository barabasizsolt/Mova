package com.barabasizsolt.domain.usecase.screen.home

import com.barabasizsolt.domain.model.HomeScreenContent
import com.barabasizsolt.domain.model.toContentItem
import com.barabasizsolt.domain.usecase.helper.movie.nowPlaying.GetNowPlayingMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.movie.topRated.GetTopRatedMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.movie.trending.GetPopularMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.movie.upcoming.GetUpcomingMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.people.GetPopularPeopleFlowUseCase
import com.barabasizsolt.movie.model.Movie
import com.barabasizsolt.people.model.People
import com.barabasizsolt.util.pagination.TailItem
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
            upcomingMovies = upcoming.filter { it !is TailItem }.map { it as Movie },
            popularMovies = popular.take(n = MAX_ITEM).map { (it as Movie).toContentItem() },
            nowPlayingMovies = nowPlaying.take(n = MAX_ITEM).map { (it as Movie).toContentItem() },
            topRatedMovies = topRated.take(n = MAX_ITEM).map { (it as Movie).toContentItem() },
            popularPeople = popularPeople.filter { it !is TailItem }.map { (it as People).toContentItem() }
        )
    }

    companion object {
        private const val MAX_ITEM: Int = 20
    }
}