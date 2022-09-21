package com.barabasizsolt.domain.useCase.helper.discover.movie

import com.barabasizsolt.discover.api.DiscoverService
import com.barabasizsolt.domain.util.FilterType
import com.barabasizsolt.domain.util.wrapToResult

class GetMovieDiscoverUseCase(private val discoverService: DiscoverService) {

    suspend operator fun invoke(
        query: String,
        region: List<String> = emptyList(),
        withGenres: List<Int> = emptyList(),
        sortBy: List<String> = listOf(FilterType.DEFAULT.value)
    ) = wrapToResult {
        if (query.isEmpty()) discoverService.getMovies(
            region = region,
            withGenres = withGenres,
            sortBy = sortBy
        ) else discoverService.searchMovies(query = query)
    }
}