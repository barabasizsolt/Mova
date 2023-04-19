package com.barabasizsolt.mova.domain.usecase.helper.movie.similar

import com.barabasizsolt.mova.api.MovieService
import com.barabasizsolt.mova.domain.util.wrapToResult
import com.barabasizsolt.mova.pager.RefreshType

class GetSimilarMoviesUseCase(private val movieService: MovieService) {

    suspend operator fun invoke(id: Int, refreshType: RefreshType) = wrapToResult {
        movieService.getSimilarMovies(id = id, refreshType = refreshType).distinctBy { it.id }
    }
}