package com.barabasizsolt.domain.useCase.helper.discover.tv

import com.barabasizsolt.discover.api.DiscoverService
import kotlinx.coroutines.flow.filterNotNull

class GetTvDiscoverFlowUseCase(private val discoverService: DiscoverService) {

    operator fun invoke(

    ) = discoverService.tvSeries.filterNotNull()
}