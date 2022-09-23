package com.barabasizsolt.domain.useCase.screen.explore

import com.barabasizsolt.domain.model.WatchableItem
import com.barabasizsolt.domain.model.toWatchableItem
import com.barabasizsolt.domain.useCase.helper.discover.movie.GetMovieDiscoverFlowUseCase
import com.barabasizsolt.domain.useCase.helper.discover.tv.GetTvDiscoverFlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class GetExploreScreenFlowUseCase(
    private val getMovieDiscoverFlowUseCase: GetMovieDiscoverFlowUseCase,
    private val getTvDiscoverFlowUseCase: GetTvDiscoverFlowUseCase
) {

    operator fun invoke(category: Category = Category.MOVIE): Flow<List<WatchableItem>> = when (category) {
        Category.MOVIE -> getMovieDiscoverFlowUseCase().map { flow -> flow.results.map { it.toWatchableItem() } }.filterNotNull()
        Category.TV -> getTvDiscoverFlowUseCase().map { flow -> flow.results.map { it.toWatchableItem() } }.filterNotNull()
    }
}