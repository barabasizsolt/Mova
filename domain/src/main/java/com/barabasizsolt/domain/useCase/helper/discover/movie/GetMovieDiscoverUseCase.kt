package com.barabasizsolt.domain.useCase.helper.discover.movie

import com.barabasizsolt.discover.api.DiscoverService
import com.barabasizsolt.domain.util.wrapToResult

class GetMovieDiscoverUseCase(private val discoverService: DiscoverService) {

    suspend operator fun invoke(query: String) = wrapToResult {
        if (query.isEmpty()) discoverService.getMovies() else discoverService.searchMovies(query = query)
    }
}