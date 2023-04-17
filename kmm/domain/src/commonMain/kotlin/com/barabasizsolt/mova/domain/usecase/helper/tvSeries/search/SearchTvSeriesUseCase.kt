package com.barabasizsolt.mova.domain.usecase.helper.tvSeries.search

import com.barabasizsolt.mova.domain.util.wrapToResult
import com.barabasizsolt.mova.explore.api.ExploreService
import com.barabasizsolt.mova.pager.RefreshType

class SearchTvSeriesUseCase(private val exploreService: ExploreService) {

    suspend operator fun invoke(query: String, refreshType: RefreshType) = wrapToResult {
        exploreService.searchTvSeries(query = query, refreshType = refreshType)
    }
}