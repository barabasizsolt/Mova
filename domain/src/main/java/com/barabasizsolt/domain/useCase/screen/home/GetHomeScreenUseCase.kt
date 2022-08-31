package com.barabasizsolt.domain.useCase.screen.home

import com.barabasizsolt.domain.useCase.helper.movie.nowPlaying.GetNowPlayingMoviesUseCase
import com.barabasizsolt.domain.useCase.helper.movie.topRated.GetTopRatedMoviesUseCase
import com.barabasizsolt.domain.useCase.helper.movie.trending.GetTrendingMoviesUseCase
import com.barabasizsolt.domain.useCase.helper.movie.upcoming.GetUpcomingMoviesUseCase
import com.barabasizsolt.domain.useCase.helper.people.GetPopularPeopleUseCase
import com.barabasizsolt.domain.util.wrapToResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

class GetHomeScreenUseCase(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getNowPlayingMoviesCase: GetNowPlayingMoviesUseCase,
    private val getPopularPeopleUseCase: GetPopularPeopleUseCase
) {

    suspend operator fun invoke(coroutineScope: CoroutineScope) = wrapToResult {
        listOf(
            coroutineScope.async { getTrendingMoviesUseCase() },
            coroutineScope.async { getUpcomingMoviesUseCase() },
            coroutineScope.async { getTopRatedMoviesUseCase() },
            coroutineScope.async { getNowPlayingMoviesCase() },
            coroutineScope.async { getPopularPeopleUseCase() },
        ).awaitAll()
    }
}