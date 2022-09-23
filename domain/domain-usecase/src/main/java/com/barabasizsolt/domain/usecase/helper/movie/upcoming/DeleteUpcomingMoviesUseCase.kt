package com.barabasizsolt.domain.usecase.helper.movie.upcoming

import com.barabasizsolt.movie.api.MovieService

class DeleteUpcomingMoviesUseCase(private val movieService: MovieService) {

    operator fun invoke() = movieService.clearUpcomingMovies()
}