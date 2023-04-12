package com.barabasizsolt.domain.usecase.helper.movie.similar

import com.barabasizsolt.domain.util.result.wrapToResult
import com.barabasizsolt.movie.api.MovieService
import com.barabasizsolt.pagination.RefreshType

class GetSimilarMoviesUseCase(private val movieService: MovieService) {

    suspend operator fun invoke(id: Int, refreshType: RefreshType) = wrapToResult {
        movieService.getSimilarMovies(id = id, refreshType = refreshType).distinctBy { it.id }
    }
}