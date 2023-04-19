package com.barabasizsolt.mova.domain.usecase.screen.home

import com.barabasizsolt.mova.domain.model.ContentItem
import com.barabasizsolt.mova.domain.model.HomeScreenContent
import com.barabasizsolt.mova.domain.model.toContentItem
import com.barabasizsolt.mova.domain.usecase.helper.genre.GetGenresFlowUseCase
import com.barabasizsolt.mova.domain.usecase.helper.movie.nowPlaying.GetNowPlayingMoviesFlowUseCase
import com.barabasizsolt.mova.domain.usecase.helper.movie.topRated.GetTopRatedMoviesFlowUseCase
import com.barabasizsolt.mova.domain.usecase.helper.movie.trending.GetPopularMoviesFlowUseCase
import com.barabasizsolt.mova.domain.usecase.helper.movie.upcoming.GetUpcomingMoviesFlowUseCase
import com.barabasizsolt.mova.domain.usecase.helper.people.GetPopularPeopleFlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import movie.model.Movie
import people.model.People

class GetHomeScreenFlowUseCase(
    private val getPopularMoviesFlowUseCase: GetPopularMoviesFlowUseCase,
    private val getUpcomingMoviesFlowUseCase: GetUpcomingMoviesFlowUseCase,
    private val getTopRatedMoviesFlowUseCase: GetTopRatedMoviesFlowUseCase,
    private val getNowPlayingMoviesFlowCase: GetNowPlayingMoviesFlowUseCase,
    private val getPopularPeopleFlowUseCase: GetPopularPeopleFlowUseCase,
    private val getGenresFlowUseCase: GetGenresFlowUseCase
) {

    operator fun invoke(): Flow<HomeScreenContent> = combine(
        getUpcomingMoviesFlowUseCase(),
        getPopularMoviesFlowUseCase(),
        getTopRatedMoviesFlowUseCase(),
        getNowPlayingMoviesFlowCase(),
        getPopularPeopleFlowUseCase(),
        getGenresFlowUseCase()
    ) { upcoming, popular, topRated, nowPlaying, popularPeople, genres ->
        HomeScreenContent(
            upcomingMovies = upcoming.filterIsInstance<Movie>(),
            popularMovies = popular.filterIsInstance<Movie>().take(n = MAX_ITEM).map { it.toContentItem() as ContentItem.Watchable },
            nowPlayingMovies = nowPlaying.filterIsInstance<Movie>().take(n = MAX_ITEM).map { it.toContentItem() as ContentItem.Watchable },
            topRatedMovies = topRated.filterIsInstance<Movie>().take(n = MAX_ITEM).map { it.toContentItem() as ContentItem.Watchable },
            popularPeople = popularPeople.filterIsInstance<People>().take(n = MAX_ITEM).map { it.toContentItem() as ContentItem.Person },
            genres = genres
        )
    }

    companion object {
        private const val MAX_ITEM: Int = 20

        private inline fun <T1, T2, T3, T4, T5, T6, R> combine(
            flow: Flow<T1>,
            flow2: Flow<T2>,
            flow3: Flow<T3>,
            flow4: Flow<T4>,
            flow5: Flow<T5>,
            flow6: Flow<T6>,
            crossinline transform: suspend (T1, T2, T3, T4, T5, T6) -> R
        ): Flow<R> {
            return combine(flow, flow2, flow3, flow4, flow5, flow6) { args: Array<*> ->
                @Suppress("UNCHECKED_CAST")
                transform(
                    args[0] as T1,
                    args[1] as T2,
                    args[2] as T3,
                    args[3] as T4,
                    args[4] as T5,
                    args[5] as T6,
                )
            }
        }
    }
}