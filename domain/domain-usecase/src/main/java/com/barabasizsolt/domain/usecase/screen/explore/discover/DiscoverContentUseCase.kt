package com.barabasizsolt.domain.usecase.screen.explore.discover

import com.barabasizsolt.domain.usecase.helper.movie.discover.DiscoverMoviesUseCase
import com.barabasizsolt.domain.usecase.helper.tvSeries.discover.DiscoverTvSeriesUseCase
import com.barabasizsolt.filter.api.Category
import com.barabasizsolt.filter.api.SortOption
import com.barabasizsolt.pagination.api.RefreshType

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
        Category.TV -> discoverTvSeriesUseCase(region = region, withGenres = withGenres, sortBy = sortBy, refreshType = refreshType)
    }
}