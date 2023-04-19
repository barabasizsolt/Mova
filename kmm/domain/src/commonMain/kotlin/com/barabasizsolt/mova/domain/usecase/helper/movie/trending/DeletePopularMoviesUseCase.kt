package com.barabasizsolt.mova.domain.usecase.helper.movie.trending

import com.barabasizsolt.mova.api.MovieService

class DeletePopularMoviesUseCase(private val movieService: MovieService) {

    operator fun invoke() = movieService.clearPopularMovies()
}