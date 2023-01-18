package com.barabasizsolt.domain.usecase.helper.discover.movie

import com.barabasizsolt.discover.api.DiscoverService
import com.barabasizsolt.domain.util.FilterType
import com.barabasizsolt.domain.util.wrapToResult
import com.barabasizsolt.util.RefreshType

class GetMovieDiscoverUseCase(private val discoverService: DiscoverService) {

    suspend operator fun invoke(
        query: String,
        region: List<String> = emptyList(),
        withGenres: List<Int> = emptyList(),
        sortBy: List<String> = listOf(FilterType.DEFAULT.value),
        refreshType: RefreshType
    ) = wrapToResult {
        if (query.isEmpty()) discoverService.getMovies(
            region = region,
            withGenres = withGenres,
            sortBy = sortBy,
            refreshType = refreshType
        ) else discoverService.searchMovies(
            query = query,
            refreshType = refreshType
        )
    }
}