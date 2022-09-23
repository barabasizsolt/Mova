package com.barabasizsolt.domain.usecase.helper.movie.topRated

import com.barabasizsolt.movie.api.MovieService
import kotlinx.coroutines.flow.filterNotNull

class GetTopRatedMoviesFlowUseCase(private val movieService: MovieService) {

    operator fun invoke() = movieService.topRatedMovies.filterNotNull()
}