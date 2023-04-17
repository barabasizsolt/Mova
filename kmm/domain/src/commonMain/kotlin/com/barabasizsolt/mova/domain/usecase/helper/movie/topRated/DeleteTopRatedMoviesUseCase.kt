package com.barabasizsolt.mova.domain.usecase.helper.movie.topRated

import com.barabasizsolt.mova.api.MovieService

class DeleteTopRatedMoviesUseCase(private val movieService: MovieService) {

    operator fun invoke() = movieService.clearTopRatedMovies()
}