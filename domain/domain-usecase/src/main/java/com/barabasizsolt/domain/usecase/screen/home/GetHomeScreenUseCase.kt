package com.barabasizsolt.domain.usecase.screen.home

import com.barabasizsolt.domain.usecase.helper.movie.nowPlaying.GetNowPlayingMoviesUseCase
import com.barabasizsolt.domain.usecase.helper.movie.topRated.GetTopRatedMoviesUseCase
import com.barabasizsolt.domain.usecase.helper.movie.trending.GetPopularMoviesUseCase
import com.barabasizsolt.domain.usecase.helper.movie.upcoming.GetUpcomingMoviesUseCase
import com.barabasizsolt.domain.usecase.helper.people.GetPopularPeopleUseCase
import com.barabasizsolt.domain.util.result.asyncWrapToResult
import com.barabasizsolt.util.RefreshType
import kotlinx.coroutines.CoroutineScope

class GetHomeScreenUseCase(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getNowPlayingMoviesCase: GetNowPlayingMoviesUseCase,
    private val getPopularPeopleUseCase: GetPopularPeopleUseCase,
) {

    suspend operator fun invoke(
        coroutineScope: CoroutineScope,
        refreshType: RefreshType
    ) = asyncWrapToResult(
        scope = coroutineScope,
        functions = listOf(
            getUpcomingMoviesUseCase(refreshType = refreshType),
            getPopularMoviesUseCase(refreshType = refreshType),
            getTopRatedMoviesUseCase(refreshType = refreshType),
            getNowPlayingMoviesCase(refreshType = refreshType),
            getPopularPeopleUseCase(refreshType = refreshType)
        )
    )
}