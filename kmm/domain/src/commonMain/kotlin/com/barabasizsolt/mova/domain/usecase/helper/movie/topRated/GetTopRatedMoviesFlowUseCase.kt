package com.barabasizsolt.mova.domain.usecase.helper.movie.topRated

import com.barabasizsolt.mova.api.MovieService
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class GetTopRatedMoviesFlowUseCase(private val movieService: MovieService) {

    operator fun invoke() = movieService.topRatedMovies.filterNotNull().map { it.distinctBy { movie -> movie.id } }
}