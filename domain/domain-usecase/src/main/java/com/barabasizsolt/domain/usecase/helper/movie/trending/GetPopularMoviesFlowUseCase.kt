package com.barabasizsolt.domain.usecase.helper.movie.trending

import com.barabasizsolt.movie.api.MovieService
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class GetPopularMoviesFlowUseCase(private val movieService: MovieService) {

    operator fun invoke() = movieService.popularMovies.filterNotNull().map { it.distinctBy { movie -> movie.id } }
}