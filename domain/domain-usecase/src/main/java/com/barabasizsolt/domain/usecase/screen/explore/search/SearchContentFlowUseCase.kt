package com.barabasizsolt.domain.usecase.screen.explore.search

import com.barabasizsolt.domain.usecase.helper.movie.search.SearchMoviesFlowUseCase
import com.barabasizsolt.domain.usecase.helper.tvSeries.search.SearchTvSeriesFlowUseCase
import com.barabasizsolt.domain.usecase.screen.explore.Category

class SearchContentFlowUseCase(
    private val searchMoviesFlowUseCase: SearchMoviesFlowUseCase,
    private val searchTvSeriesFlowUseCase: SearchTvSeriesFlowUseCase
) {

    operator fun invoke(category: Category) = when (category) {
        Category.MOVIE -> searchMoviesFlowUseCase()
        Category.TV -> searchTvSeriesFlowUseCase()
    }
}