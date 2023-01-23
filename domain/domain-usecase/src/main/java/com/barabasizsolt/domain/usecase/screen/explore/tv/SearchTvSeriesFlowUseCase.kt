package com.barabasizsolt.domain.usecase.screen.explore.tv

import com.barabasizsolt.discover.api.ExploreService
import kotlinx.coroutines.flow.filterNotNull

class SearchTvSeriesFlowUseCase(private val exploreService: ExploreService) {

    operator fun invoke() = exploreService.searchTvSeries.filterNotNull()
}