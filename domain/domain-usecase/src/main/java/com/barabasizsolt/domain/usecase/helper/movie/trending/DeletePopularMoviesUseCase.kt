package com.barabasizsolt.domain.usecase.helper.movie.trending

import com.barabasizsolt.movie.api.MovieService

class DeletePopularMoviesUseCase(private val movieService: MovieService) {

    operator fun invoke() = movieService.clearPopularMovies()
}