package com.barabasizsolt.domain.usecase.helper.movie.discover

import com.barabasizsolt.discover.api.ExploreService
import com.barabasizsolt.domain.util.result.wrapToResult
import com.barabasizsolt.filter.api.SortOption
import com.barabasizsolt.pagination.RefreshType

class DiscoverMoviesUseCase(private val exploreService: ExploreService) {

    suspend operator fun invoke(
        region: List<String> = emptyList(),
        withGenres: List<Int> = emptyList(),
        sortBy: List<String> = listOf(SortOption.DEFAULT.value),
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