package com.barabasizsolt.mova.domain.usecase.screen.seeall

import com.barabasizsolt.mova.api.MovieService

class ClearSeeAllUseCase(private val movieService: MovieService) {

    operator fun invoke() {
        movieService.clearPopularMovies()
    }
}