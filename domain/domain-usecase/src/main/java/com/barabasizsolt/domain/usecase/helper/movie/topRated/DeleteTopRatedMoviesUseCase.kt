package com.barabasizsolt.domain.usecase.helper.movie.topRated

import com.barabasizsolt.movie.api.MovieService

class DeleteTopRatedMoviesUseCase(private val movieService: MovieService) {

    operator fun invoke() = movieService.clearTopRatedMovies()
}