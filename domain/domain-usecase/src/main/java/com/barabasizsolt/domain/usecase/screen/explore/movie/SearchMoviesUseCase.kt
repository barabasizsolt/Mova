package com.barabasizsolt.domain.usecase.screen.explore.movie

import com.barabasizsolt.discover.api.ExploreService
import com.barabasizsolt.domain.util.result.wrapToResult
import com.barabasizsolt.util.RefreshType

class SearchMoviesUseCase(private val exploreService: ExploreService) {

    suspend operator fun invoke(query: String, refreshType: RefreshType) = wrapToResult {
        exploreService.searchMovies(
            query = query,
            refreshType = refreshType
        )
    }
}