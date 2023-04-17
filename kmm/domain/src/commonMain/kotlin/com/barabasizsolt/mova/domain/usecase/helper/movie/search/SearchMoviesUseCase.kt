package com.barabasizsolt.mova.domain.usecase.helper.movie.search

import com.barabasizsolt.mova.domain.util.wrapToResult
import com.barabasizsolt.mova.explore.api.ExploreService
import com.barabasizsolt.mova.pager.RefreshType

class SearchMoviesUseCase(private val exploreService: ExploreService) {

    suspend operator fun invoke(query: String, refreshType: RefreshType) = wrapToResult {
        exploreService.searchMovies(
            query = query,
            refreshType = refreshType
        )
    }
}