package com.barabasizsolt.domain.useCase.movie.upcoming

import com.barabasizsolt.movie.api.MovieService
import kotlinx.coroutines.flow.filterNotNull

class GetUpcomingMoviesFlowUseCase(private val movieService: MovieService) {

    suspend operator fun invoke() = movieService.upcomingMovies.filterNotNull()
}