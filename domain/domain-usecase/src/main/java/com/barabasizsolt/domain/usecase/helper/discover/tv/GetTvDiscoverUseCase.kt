package com.barabasizsolt.domain.usecase.helper.discover.tv

import com.barabasizsolt.discover.api.DiscoverService
import com.barabasizsolt.domain.util.FilterType
import com.barabasizsolt.domain.util.wrapToResult
import com.barabasizsolt.util.RefreshType

class GetTvDiscoverUseCase(private val discoverService: DiscoverService) {

    suspend operator fun invoke(
        query: String,
        region: List<String> = emptyList(),
        withGenres: List<Int> = emptyList(),
        sortBy: List<String> = listOf(FilterType.DEFAULT.value),
        refreshType: RefreshType
    ) = wrapToResult {
        if (query.isEmpty()) discoverService.getTvSeries(
            region = region,
            withGenres = withGenres,
            sortBy = sortBy,
            refreshType = refreshType
        ) else discoverService.searchTvSeries(query = query, refreshType = refreshType)
    }
}