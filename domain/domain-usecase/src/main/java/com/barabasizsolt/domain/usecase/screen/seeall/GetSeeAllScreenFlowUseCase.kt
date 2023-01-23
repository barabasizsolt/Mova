package com.barabasizsolt.domain.usecase.screen.seeall

import com.barabasizsolt.domain.model.ContentItem
import com.barabasizsolt.domain.model.toContentItem
import com.barabasizsolt.domain.usecase.helper.movie.nowPlaying.GetNowPlayingMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.movie.topRated.GetTopRatedMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.movie.trending.GetPopularMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.people.GetPopularPeopleFlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

class GetSeeAllScreenFlowUseCase(
    private val getPopularMoviesFlowUseCase: GetPopularMoviesFlowUseCase,
    private val getTopRatedMoviesFlowUseCase: GetTopRatedMoviesFlowUseCase,
    private val getNowPlayingMoviesFlowCase: GetNowPlayingMoviesFlowUseCase,
    private val getPopularPeopleFlowUseCase: GetPopularPeopleFlowUseCase
) {
    operator fun invoke(contentType: String): Flow<List<ContentItem>> = when (contentType) {
        SeeAllContentType.POPULAR_MOVIES.name -> getPopularMoviesFlowUseCase().map { item -> item.map { it.toContentItem() } }
        SeeAllContentType.POPULAR_PEOPLE.name -> getPopularPeopleFlowUseCase().map { item -> item.map { it.toContentItem() } }
        SeeAllContentType.NOW_PLAYING_MOVIES.name -> getNowPlayingMoviesFlowCase().map { item -> item.map { it.toContentItem() } }
        SeeAllContentType.TOP_RATED_MOVIES.name -> getTopRatedMoviesFlowUseCase().map { item -> item.map { it.toContentItem() } }
        else -> emptyFlow()
    }
}