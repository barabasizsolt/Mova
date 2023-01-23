package com.barabasizsolt.domain.usecase.helper.movie.search

import com.barabasizsolt.discover.api.ExploreService

class DeleteSearchMovieUseCase(private val exploreService: ExploreService) {

    operator fun invoke() = exploreService.clearSearchMovies()
}