package com.barabasizsolt.domain.usecase.helper.discover.tv

import com.barabasizsolt.discover.api.ExploreService

class DeleteSearchTvSeriesUseCase(private val exploreService: ExploreService) {

    operator fun invoke() = exploreService.clearSearchTvSeries()
}