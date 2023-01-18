package com.barabasizsolt.domain.usecase.screen.seeall

import com.barabasizsolt.domain.model.WatchableItem
import com.barabasizsolt.domain.model.toWatchableItem
import com.barabasizsolt.domain.usecase.helper.movie.nowPlaying.GetNowPlayingMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.movie.topRated.GetTopRatedMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.movie.trending.GetPopularMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.people.GetPopularPeopleFlowUseCase
import com.barabasizsolt.movie.model.Movie
import com.barabasizsolt.people.model.People
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

class GetSeeAllScreenFlowUseCase(
    private val getPopularMoviesFlowUseCase: GetPopularMoviesFlowUseCase,
    private val getTopRatedMoviesFlowUseCase: GetTopRatedMoviesFlowUseCase,
    private val getNowPlayingMoviesFlowCase: GetNowPlayingMoviesFlowUseCase,
    private val getPopularPeopleFlowUseCase: GetPopularPeopleFlowUseCase
) {
    operator fun invoke(contentType: String): Flow<List<WatchableItem>> = when (contentType) {
        SeeAllContentType.POPULAR_MOVIES.name ->
            getPopularMoviesFlowUseCase().map { popularMovies -> popularMovies.map { (it as Movie).toWatchableItem() }  }
        SeeAllContentType.POPULAR_PEOPLE.name ->
            getPopularPeopleFlowUseCase().map { popularPeople -> popularPeople.map { (it as People).toWatchableItem() }  }
        SeeAllContentType.NOW_PLAYING_MOVIES.name ->
            getNowPlayingMoviesFlowCase().map { nowPlayingMovies -> nowPlayingMovies.map { (it as Movie).toWatchableItem() }  }
        SeeAllContentType.TOP_RATED_MOVIES.name ->
            getTopRatedMoviesFlowUseCase().map { topRatedMovies -> topRatedMovies.map { (it as Movie).toWatchableItem() }  }
        else -> emptyFlow()
    }
}