package com.barabasizsolt.domain.usecase.screen.seeall

import com.barabasizsolt.domain.usecase.helper.movie.nowPlaying.GetNowPlayingMoviesUseCase
import com.barabasizsolt.domain.usecase.helper.movie.topRated.GetTopRatedMoviesUseCase
import com.barabasizsolt.domain.usecase.helper.movie.trending.GetPopularMoviesUseCase
import com.barabasizsolt.domain.usecase.helper.people.GetPopularPeopleUseCase
import com.barabasizsolt.domain.util.wrapToResult
import com.barabasizsolt.util.RefreshType

class GetSeeAllScreenUseCase(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getNowPlayingMoviesCase: GetNowPlayingMoviesUseCase,
    private val getPopularPeopleUseCase: GetPopularPeopleUseCase
) {
    suspend operator fun invoke(refreshType: RefreshType, contentType: String) = wrapToResult {
        when (contentType) {
            SeeAllContentType.POPULAR_MOVIES.name -> getPopularMoviesUseCase(refreshType = refreshType)
            SeeAllContentType.POPULAR_PEOPLE.name -> getPopularPeopleUseCase()
            SeeAllContentType.NOW_PLAYING_MOVIES.name -> getNowPlayingMoviesCase(refreshType = refreshType)
            SeeAllContentType.TOP_RATED_MOVIES.name -> getTopRatedMoviesUseCase(refreshType = refreshType)
            else -> throw IllegalStateException("Invalid SeeAllContentType: $contentType")
        }
    }
}