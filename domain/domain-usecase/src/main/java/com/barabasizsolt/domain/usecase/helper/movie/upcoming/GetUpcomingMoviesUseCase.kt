package com.barabasizsolt.domain.useCase.helper.movie.upcoming

import com.barabasizsolt.domain.util.wrapToResult
import com.barabasizsolt.movie.api.MovieService

class GetUpcomingMoviesUseCase(private val movieService: MovieService) {

    suspend operator fun invoke(page: Int = 1) = wrapToResult { movieService.getUpcomingMovies(page = page) }
}