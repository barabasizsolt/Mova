package com.barabasizsolt.mova.domain.usecase.helper.movie.trending

import com.barabasizsolt.mova.api.MovieService
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class GetPopularMoviesFlowUseCase(private val movieService: MovieService) {

    operator fun invoke() = movieService.popularMovies.filterNotNull().map { it.distinctBy { movie -> movie.id } }
}