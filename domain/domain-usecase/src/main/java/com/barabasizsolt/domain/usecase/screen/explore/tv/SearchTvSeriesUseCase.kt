package com.barabasizsolt.domain.usecase.screen.explore.tv

import com.barabasizsolt.discover.api.ExploreService
import com.barabasizsolt.domain.util.result.wrapToResult
import com.barabasizsolt.util.RefreshType

class SearchTvSeriesUseCase(private val exploreService: ExploreService) {

    suspend operator fun invoke(query: String, refreshType: RefreshType) = wrapToResult {
        exploreService.searchTvSeries(query = query, refreshType = refreshType)
    }
}