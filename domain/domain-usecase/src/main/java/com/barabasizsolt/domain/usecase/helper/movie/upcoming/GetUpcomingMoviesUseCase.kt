package com.barabasizsolt.domain.usecase.helper.movie.upcoming

import com.barabasizsolt.domain.util.wrapToResult
import com.barabasizsolt.movie.api.MovieService

class GetUpcomingMoviesUseCase(private val movieService: MovieService) {

    suspend operator fun invoke() = wrapToResult { movieService.getUpcomingMovies() }
}