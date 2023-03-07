package com.barabasizsolt.domain.usecase.screen.explore.search

import com.barabasizsolt.domain.usecase.helper.movie.search.SearchMoviesUseCase
import com.barabasizsolt.domain.usecase.helper.tvSeries.search.SearchTvSeriesUseCase
import com.barabasizsolt.filter.api.Category
import com.barabasizsolt.pagination.api.RefreshType

class SearchContentUseCase(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val searchTvSeriesUseCase: SearchTvSeriesUseCase
) {

    suspend operator fun invoke(category: Category, query: String, refreshType: RefreshType) = when (category) {
        Category.MOVIE -> searchMoviesUseCase(query = query, refreshType = refreshType)
        Category.TV -> searchTvSeriesUseCase(query = query, refreshType = refreshType)
    }
}