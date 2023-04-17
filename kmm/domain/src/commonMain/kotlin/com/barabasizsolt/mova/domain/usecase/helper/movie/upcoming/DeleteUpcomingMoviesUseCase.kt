package com.barabasizsolt.mova.domain.usecase.helper.movie.upcoming

import com.barabasizsolt.mova.api.MovieService

class DeleteUpcomingMoviesUseCase(private val movieService: MovieService) {

    operator fun invoke() = movieService.clearUpcomingMovies()
}