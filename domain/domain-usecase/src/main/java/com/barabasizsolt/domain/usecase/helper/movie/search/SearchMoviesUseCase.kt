package com.barabasizsolt.domain.usecase.helper.movie.search

import com.barabasizsolt.discover.api.ExploreService
import com.barabasizsolt.domain.util.result.wrapToResult
import com.barabasizsolt.pagination.api.RefreshType

class SearchMoviesUseCase(private val exploreService: ExploreService) {

    suspend operator fun invoke(query: String, refreshType: RefreshType) = wrapToResult {
        exploreService.searchMovies(
            query = query,
            refreshType = refreshType
        )
    }
}