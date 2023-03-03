package com.barabasizsolt.domain.usecase.screen.explore.discover

import com.barabasizsolt.domain.model.ContentItem
import com.barabasizsolt.domain.usecase.helper.movie.discover.DiscoverMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.tvSeries.discover.DiscoverTvSeriesFlowUseCase
import com.barabasizsolt.domain.usecase.screen.explore.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class DiscoverContentFlowUseCase(
    private val discoverMoviesFlowUseCase: DiscoverMoviesFlowUseCase,
    private val discoverTvSeriesFlowUseCase: DiscoverTvSeriesFlowUseCase
) {

    operator fun invoke(category: Category): Flow<List<ContentItem>> = when (category) {
        Category.MOVIE -> discoverMoviesFlowUseCase()
        Category.TV -> discoverTvSeriesFlowUseCase()
        Category.ALL_CATEGORY -> emptyFlow()
    }
}