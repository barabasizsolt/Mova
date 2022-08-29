package com.barabasizsolt.domain.useCase.helper.movie.upcoming

import com.barabasizsolt.movie.api.MovieService
import kotlinx.coroutines.flow.filterNotNull

class GetUpcomingMoviesFlowUseCase(private val movieService: MovieService) {

    operator fun invoke() = movieService.upcomingMovies.filterNotNull()
}