package com.barabasizsolt.mova.domain.usecase.screen.explore.search

import category.Category
import com.barabasizsolt.mova.domain.usecase.helper.movie.search.SearchMoviesFlowUseCase
import com.barabasizsolt.mova.domain.usecase.helper.tvSeries.search.SearchTvSeriesFlowUseCase

class SearchContentFlowUseCase(
    private val searchMoviesFlowUseCase: SearchMoviesFlowUseCase,
    private val searchTvSeriesFlowUseCase: SearchTvSeriesFlowUseCase
) {

    operator fun invoke(category: Category) = when (category) {
        Category.MOVIE -> searchMoviesFlowUseCase()
        Category.TV -> searchTvSeriesFlowUseCase()
    }
}