package com.barabasizsolt.domain.usecase.helper.movie.similar

import com.barabasizsolt.movie.api.MovieService

class DeleteSimilarMoviesUseCase(private val movieService: MovieService) {

    operator fun invoke() = movieService.clearSimilarMovies()
}