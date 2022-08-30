package com.barabasizsolt.domain.useCase.helper.movie.topRated

import com.barabasizsolt.domain.util.wrapToResult
import com.barabasizsolt.movie.api.MovieService

class GetTopRatedMoviesUseCase(private val movieService: MovieService) {

    suspend operator fun invoke(page: Int = 1) = wrapToResult { movieService.getTopRatedMovies(page = page) }
}