package com.barabasizsolt.domain.usecase.helper.movie.trending

import com.barabasizsolt.movie.api.MovieService
import kotlinx.coroutines.flow.filterNotNull

class GetTrendingMoviesFlowUseCase(private val movieService: MovieService) {

    operator fun invoke() = movieService.trendingMovies.filterNotNull()
}