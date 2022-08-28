package com.barabasizsolt.domain.useCase.discover.movie

import com.barabasizsolt.discover.api.DiscoverService
import com.barabasizsolt.domain.util.wrapToResult

class GetMovieDiscoverUseCase(private val discoverService: DiscoverService) {

    suspend operator fun invoke() = wrapToResult { discoverService.getMovies() }
}