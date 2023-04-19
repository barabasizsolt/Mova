package com.barabasizsolt.mova.domain.usecase.helper.tvSeries.search

import com.barabasizsolt.mova.explore.api.ExploreService

class DeleteSearchTvSeriesUseCase(private val exploreService: ExploreService) {

    operator fun invoke() = exploreService.clearSearchTvSeries()
}