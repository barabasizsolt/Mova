package com.barabasizsolt.mova.domain.usecase.screen.home

import com.barabasizsolt.mova.domain.usecase.helper.genre.GetGenresUseCase
import com.barabasizsolt.mova.domain.usecase.helper.movie.nowPlaying.GetNowPlayingMoviesUseCase
import com.barabasizsolt.mova.domain.usecase.helper.movie.topRated.GetTopRatedMoviesUseCase
import com.barabasizsolt.mova.domain.usecase.helper.movie.trending.GetPopularMoviesUseCase
import com.barabasizsolt.mova.domain.usecase.helper.movie.upcoming.GetUpcomingMoviesUseCase
import com.barabasizsolt.mova.domain.usecase.helper.people.GetPopularPeopleUseCase
import com.barabasizsolt.mova.domain.util.asyncWrapToResult
import com.barabasizsolt.mova.pager.RefreshType

class GetHomeScreenUseCase(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getNowPlayingMoviesCase: GetNowPlayingMoviesUseCase,
    private val getPopularPeopleUseCase: GetPopularPeopleUseCase,
    private val getGenresUseCase: GetGenresUseCase
) {

    suspend operator fun invoke(refreshType: RefreshType) = asyncWrapToResult(
        functions = listOf(
            getUpcomingMoviesUseCase(refreshType = refreshType),
            getPopularMoviesUseCase(refreshType = refreshType),
            getTopRatedMoviesUseCase(refreshType = refreshType),
            getNowPlayingMoviesCase(refreshType = refreshType),
            getPopularPeopleUseCase(refreshType = refreshType),
            getGenresUseCase()
        )
    )
}