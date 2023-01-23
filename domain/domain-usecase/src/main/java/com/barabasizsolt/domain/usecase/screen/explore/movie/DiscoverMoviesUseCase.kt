package com.barabasizsolt.domain.usecase.screen.explore.movie

import com.barabasizsolt.discover.api.ExploreService
import com.barabasizsolt.domain.util.FilterType
import com.barabasizsolt.domain.util.result.wrapToResult
import com.barabasizsolt.util.RefreshType

class DiscoverMoviesUseCase(private val exploreService: ExploreService) {

    suspend operator fun invoke(
        region: List<String> = emptyList(),
        withGenres: List<Int> = emptyList(),
        sortBy: List<String> = listOf(FilterType.DEFAULT.value),
        refreshType: RefreshType
    ) = wrapToResult {
        exploreService.getMovies(
            region = region,
            withGenres = withGenres,
            sortBy = sortBy,
            refreshType = refreshType
        )
    }
}