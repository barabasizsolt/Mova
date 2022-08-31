package com.barabasizsolt.domain.useCase.helper.discover.movie

import com.barabasizsolt.discover.api.DiscoverService

class DeleteMovieDiscoverUseCase(private val discoverService: DiscoverService) {

    operator fun invoke() = discoverService.clearMovies()
}