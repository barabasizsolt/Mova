package com.barabasizsolt.domain.usecase.helper.tvSeries.discover

import com.barabasizsolt.discover.api.ExploreService
import com.barabasizsolt.domain.util.result.wrapToResult
import com.barabasizsolt.filter.api.SortOption
import com.barabasizsolt.pagination.api.RefreshType

class DiscoverTvSeriesUseCase(private val exploreService: ExploreService) {

    suspend operator fun invoke(
        withGenres: List<Int> = emptyList(),
        sortBy: List<String> = listOf(SortOption.DEFAULT.value),
        refreshType: RefreshType
    ) = wrapToResult {
        exploreService.getTvSeries(
            withGenres = withGenres,
            sortBy = sortBy,
            refreshType = refreshType
        )
    }
}