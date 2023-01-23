package com.barabasizsolt.domain.usecase.helper.tvSeries.discover

import com.barabasizsolt.discover.api.ExploreService
import com.barabasizsolt.domain.util.FilterType
import com.barabasizsolt.domain.util.result.wrapToResult
import com.barabasizsolt.pagination.api.RefreshType

class DiscoverTvSeriesUseCase(private val exploreService: ExploreService) {

    suspend operator fun invoke(
        region: List<String> = emptyList(),
        withGenres: List<Int> = emptyList(),
        sortBy: List<String> = listOf(FilterType.DEFAULT.value),
        refreshType: RefreshType
    ) = wrapToResult {
        exploreService.getTvSeries(
            region = region,
            withGenres = withGenres,
            sortBy = sortBy,
            refreshType = refreshType
        )
    }
}