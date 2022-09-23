package com.barabasizsolt.domain.usecase.screen.home

import com.barabasizsolt.domain.usecase.helper.movie.nowPlaying.GetNowPlayingMoviesUseCase
import com.barabasizsolt.domain.usecase.helper.movie.topRated.GetTopRatedMoviesUseCase
import com.barabasizsolt.domain.usecase.helper.movie.trending.GetTrendingMoviesUseCase
import com.barabasizsolt.domain.usecase.helper.movie.upcoming.GetUpcomingMoviesUseCase
import com.barabasizsolt.domain.usecase.helper.people.GetPopularPeopleUseCase
import com.barabasizsolt.domain.util.asyncWrapToResult
import kotlinx.coroutines.CoroutineScope

class GetHomeScreenUseCase(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getNowPlayingMoviesCase: GetNowPlayingMoviesUseCase,
    private val getPopularPeopleUseCase: GetPopularPeopleUseCase,
) {

    suspend operator fun invoke(coroutineScope: CoroutineScope) = asyncWrapToResult(
        scope = coroutineScope,
        functions = listOf(
            getTrendingMoviesUseCase(),
            getUpcomingMoviesUseCase(),
            getTopRatedMoviesUseCase(),
            getNowPlayingMoviesCase(),
            getPopularPeopleUseCase()
        )
    )
}