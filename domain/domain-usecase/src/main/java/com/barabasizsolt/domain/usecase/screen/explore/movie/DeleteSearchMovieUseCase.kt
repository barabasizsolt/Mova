package com.barabasizsolt.domain.usecase.screen.explore.movie

import com.barabasizsolt.discover.api.ExploreService

class DeleteSearchMovieUseCase(private val exploreService: ExploreService) {

    operator fun invoke() = exploreService.clearSearchMovies()
}