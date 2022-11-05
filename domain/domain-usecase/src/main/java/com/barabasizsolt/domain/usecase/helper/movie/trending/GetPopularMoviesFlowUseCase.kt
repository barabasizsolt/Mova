package com.barabasizsolt.domain.usecase.helper.movie.trending

import com.barabasizsolt.movie.api.MovieService
import kotlinx.coroutines.flow.filterNotNull

class GetPopularMoviesFlowUseCase(private val movieService: MovieService) {

    operator fun invoke() = movieService.popularMovies.filterNotNull()
}