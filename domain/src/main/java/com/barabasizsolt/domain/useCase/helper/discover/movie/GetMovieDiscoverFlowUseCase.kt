package com.barabasizsolt.domain.useCase.helper.discover.movie

import com.barabasizsolt.discover.api.DiscoverService
import kotlinx.coroutines.flow.filterNotNull

class GetMovieDiscoverFlowUseCase(private val discoverService: DiscoverService) {

    operator fun invoke() = discoverService.movies.filterNotNull()
}