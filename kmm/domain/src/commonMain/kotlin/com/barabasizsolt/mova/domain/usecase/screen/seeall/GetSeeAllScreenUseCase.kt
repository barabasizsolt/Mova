package com.barabasizsolt.mova.domain.usecase.screen.seeall

import com.barabasizsolt.mova.domain.usecase.helper.movie.nowPlaying.GetNowPlayingMoviesUseCase
import com.barabasizsolt.mova.domain.usecase.helper.movie.topRated.GetTopRatedMoviesUseCase
import com.barabasizsolt.mova.domain.usecase.helper.movie.trending.GetPopularMoviesUseCase
import com.barabasizsolt.mova.domain.usecase.helper.people.GetPopularPeopleUseCase
import com.barabasizsolt.mova.pager.RefreshType

class GetSeeAllScreenUseCase(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getNowPlayingMoviesCase: GetNowPlayingMoviesUseCase,
    private val getPopularPeopleUseCase: GetPopularPeopleUseCase
) {
    suspend operator fun invoke(refreshType: RefreshType, contentType: String) = when (contentType) {
        SeeAllContentType.POPULAR_MOVIES.name -> getPopularMoviesUseCase(refreshType = refreshType)
        SeeAllContentType.POPULAR_PEOPLE.name -> getPopularPeopleUseCase(refreshType = refreshType)
        SeeAllContentType.NOW_PLAYING_MOVIES.name -> getNowPlayingMoviesCase(refreshType = refreshType)
        SeeAllContentType.TOP_RATED_MOVIES.name -> getTopRatedMoviesUseCase(refreshType = refreshType)
        else -> throw IllegalStateException("Invalid SeeAllContentType: $contentType")
    }
}