package com.barabasizsolt.domain.usecase.screen.seeall

import com.barabasizsolt.domain.usecase.helper.movie.nowPlaying.GetNowPlayingMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.movie.topRated.GetTopRatedMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.movie.trending.GetPopularMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.people.GetPopularPeopleFlowUseCase
import kotlinx.coroutines.flow.emptyFlow

class GetSeeAllScreenFlowUseCase(
    private val getPopularMoviesFlowUseCase: GetPopularMoviesFlowUseCase,
    private val getTopRatedMoviesFlowUseCase: GetTopRatedMoviesFlowUseCase,
    private val getNowPlayingMoviesFlowCase: GetNowPlayingMoviesFlowUseCase,
    private val getPopularPeopleFlowUseCase: GetPopularPeopleFlowUseCase
) {
    operator fun invoke(contentType: String) = when (contentType) {
        SeeAllContentType.POPULAR_MOVIES.name -> getPopularMoviesFlowUseCase()
        SeeAllContentType.POPULAR_PEOPLE.name -> getPopularPeopleFlowUseCase()
        SeeAllContentType.NOW_PLAYING_MOVIES.name -> getNowPlayingMoviesFlowCase()
        SeeAllContentType.TOP_RATED_MOVIES.name -> getTopRatedMoviesFlowUseCase()
        else -> emptyFlow()
    }
}