package com.barabasizsolt.domain.useCase.helper.movie.trending

import com.barabasizsolt.movie.api.MovieService

class DeleteTrendingMoviesUseCase(private val movieService: MovieService) {

    operator fun invoke() = movieService.clearTrendingMovies()
}