package com.barabasizsolt.domain.usecase.helper.movie.trending

import com.barabasizsolt.domain.util.wrapToResult
import com.barabasizsolt.movie.api.MovieService

class GetTrendingMoviesUseCase(private val movieService: MovieService) {

    suspend operator fun invoke(page: Int = 1) = wrapToResult { movieService.getTrendingMovies(page = page) }
}