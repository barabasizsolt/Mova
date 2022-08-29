package com.barabasizsolt.domain.useCase.movie.upcoming

import com.barabasizsolt.domain.util.wrapToResult
import com.barabasizsolt.movie.api.MovieService

class GetUpcomingMoviesUseCase(private val movieService: MovieService) {

    suspend operator fun invoke(page: Int) = wrapToResult { movieService.getUpcomingMovies(page = page) }
}