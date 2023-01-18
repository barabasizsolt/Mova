package com.barabasizsolt.domain.usecase.screen.explore

import com.barabasizsolt.domain.model.WatchableItem
import com.barabasizsolt.domain.model.toWatchableItem
import com.barabasizsolt.domain.usecase.helper.discover.movie.GetMovieDiscoverFlowUseCase
import com.barabasizsolt.domain.usecase.helper.discover.tv.GetTvDiscoverFlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import com.barabasizsolt.movie.model.Movie
import com.barabasizsolt.tv.modell.TvSeries

class GetExploreScreenFlowUseCase(
    private val getMovieDiscoverFlowUseCase: GetMovieDiscoverFlowUseCase,
    private val getTvDiscoverFlowUseCase: GetTvDiscoverFlowUseCase
) {

    operator fun invoke(category: Category): Flow<List<WatchableItem>> = when (category) {
        Category.MOVIE -> getMovieDiscoverFlowUseCase().map { movie -> movie.map { (it as Movie).toWatchableItem() } }.filterNotNull()
        Category.TV -> getTvDiscoverFlowUseCase().map { tv -> tv.map { (it as TvSeries).toWatchableItem() } }.filterNotNull()
    }
}