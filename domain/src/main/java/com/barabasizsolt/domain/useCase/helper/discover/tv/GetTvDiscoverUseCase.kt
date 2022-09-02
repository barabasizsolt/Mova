package com.barabasizsolt.domain.useCase.helper.discover.tv

import com.barabasizsolt.discover.api.DiscoverService
import com.barabasizsolt.domain.util.wrapToResult

class GetTvDiscoverUseCase(private val discoverService: DiscoverService) {

    suspend operator fun invoke() = wrapToResult { discoverService.getTvSeries() }
}