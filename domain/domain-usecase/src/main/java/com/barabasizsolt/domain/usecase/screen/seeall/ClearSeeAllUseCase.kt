package com.barabasizsolt.domain.usecase.screen.seeall

import com.barabasizsolt.movie.api.MovieService

class ClearSeeAllUseCase(private val movieService: MovieService) {

    operator fun invoke() {
        movieService.clearPopularMovies()
    }
}