package com.barabasizsolt.mova.domain.usecase.screen.explore.discover

import category.Category
import com.barabasizsolt.mova.domain.usecase.helper.movie.discover.DiscoverMoviesUseCase
import com.barabasizsolt.mova.domain.usecase.helper.tvSeries.discover.DiscoverTvSeriesUseCase
import com.barabasizsolt.mova.filter.api.SortOption
import com.barabasizsolt.mova.pager.RefreshType

class DiscoverContentUseCase(
    private val discoverMoviesUseCase: DiscoverMoviesUseCase,
    private val discoverTvSeriesUseCase: DiscoverTvSeriesUseCase
) {

    suspend operator fun invoke(
        category: Category,
        region: List<String> = emptyList(),
        withGenres: List<Int> = emptyList(),
        sortBy: List<String> = listOf(SortOption.DEFAULT.value),
        refreshType: RefreshType
    ) = when (category) {
        Category.MOVIE -> discoverMoviesUseCase(region = region, withGenres = withGenres, sortBy = sortBy, refreshType = refreshType)
        Category.TV -> discoverTvSeriesUseCase(withGenres = withGenres, sortBy = sortBy, refreshType = refreshType)
    }
}