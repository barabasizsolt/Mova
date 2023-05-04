package com.barabasizsolt.mova.domain.usecase.screen.explore.search

import category.Category
import com.barabasizsolt.mova.domain.usecase.helper.movie.search.SearchMoviesUseCase
import com.barabasizsolt.mova.domain.usecase.helper.tvSeries.search.SearchTvSeriesUseCase
import com.barabasizsolt.mova.pager.RefreshType

class SearchContentUseCase(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val searchTvSeriesUseCase: SearchTvSeriesUseCase
) {

    suspend operator fun invoke(category: Category, query: String, refreshType: RefreshType) = when (category) {
        Category.MOVIE -> searchMoviesUseCase(query = query, refreshType = refreshType)
        Category.TV -> searchTvSeriesUseCase(query = query, refreshType = refreshType)
    }
}