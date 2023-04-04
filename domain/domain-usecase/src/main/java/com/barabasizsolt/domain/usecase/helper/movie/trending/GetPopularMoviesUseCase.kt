package com.barabasizsolt.domain.usecase.helper.movie.trending

import com.barabasizsolt.domain.util.result.wrapToResult
import com.barabasizsolt.movie.api.MovieService
import com.barabasizsolt.pagination.RefreshType

class GetPopularMoviesUseCase(private val movieService: MovieService) {

    suspend operator fun invoke(refreshType: RefreshType) = wrapToResult {
        movieService.getPopularMovies(refreshType = refreshType).distinctBy { it.id }
    }
}