package com.barabasizsolt.mova.domain.usecase.screen.explore.discover

import category.Category
import com.barabasizsolt.mova.domain.model.ContentItem
import com.barabasizsolt.mova.domain.usecase.helper.movie.discover.DiscoverMoviesFlowUseCase
import com.barabasizsolt.mova.domain.usecase.helper.tvSeries.discover.DiscoverTvSeriesFlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.last

class DiscoverContentFlowUseCase(
    private val discoverMoviesFlowUseCase: DiscoverMoviesFlowUseCase,
    private val discoverTvSeriesFlowUseCase: DiscoverTvSeriesFlowUseCase
) {

    operator fun invoke(category: Category): Flow<List<ContentItem>> = when (category) {
        Category.MOVIE -> discoverMoviesFlowUseCase()
        Category.TV -> discoverTvSeriesFlowUseCase()
    }
}