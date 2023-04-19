package com.barabasizsolt.mova.domain.usecase.helper.movie.similar

import com.barabasizsolt.mova.api.MovieService
import kotlinx.coroutines.flow.filterNotNull

class GetSimilarMoviesFlowUseCase(private val movieService: MovieService) {

    operator fun invoke() = movieService.similarMovies.filterNotNull()
}