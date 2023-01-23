package com.barabasizsolt.domain.usecase.screen.explore.movie

import com.barabasizsolt.discover.api.ExploreService
import kotlinx.coroutines.flow.filterNotNull

class DiscoverMoviesFlowUseCase(private val exploreService: ExploreService) {

    operator fun invoke() = exploreService.discoverMovies.filterNotNull()
}