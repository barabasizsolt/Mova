package com.barabasizsolt.domain.usecase.helper.movie.similar

import com.barabasizsolt.movie.api.MovieService
import kotlinx.coroutines.flow.filterNotNull

class GetSimilarMoviesFlowUseCase(private val movieService: MovieService) {

    operator fun invoke() = movieService.similarMovies.filterNotNull()
}