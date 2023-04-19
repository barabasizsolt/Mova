package com.barabasizsolt.mova.domain.usecase.helper.movie.discover

import com.barabasizsolt.mova.domain.util.wrapToResult
import com.barabasizsolt.mova.explore.api.ExploreService
import com.barabasizsolt.mova.filter.api.SortOption
import com.barabasizsolt.mova.pager.RefreshType

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