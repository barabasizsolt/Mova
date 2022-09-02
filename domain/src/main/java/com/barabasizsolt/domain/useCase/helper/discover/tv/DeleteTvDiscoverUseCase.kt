package com.barabasizsolt.domain.useCase.helper.discover.tv

import com.barabasizsolt.discover.api.DiscoverService

class DeleteTvDiscoverUseCase(private val discoverService: DiscoverService) {

    operator fun invoke() = discoverService.clearTvSeries()
}