package com.barabasizsolt.mova.domain.usecase.helper.movie.similar

import com.barabasizsolt.mova.api.MovieService

class DeleteSimilarMoviesUseCase(private val movieService: MovieService) {

    operator fun invoke() = movieService.clearSimilarMovies()
}