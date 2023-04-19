package com.barabasizsolt.mova.domain.usecase.helper.movie.search

import com.barabasizsolt.mova.explore.api.ExploreService

class DeleteSearchMovieUseCase(private val exploreService: ExploreService) {

    operator fun invoke() = exploreService.clearSearchMovies()
}