package com.barabasizsolt.mova.domain.usecase.helper.movie.topRated

import com.barabasizsolt.mova.api.MovieService
import com.barabasizsolt.mova.domain.util.wrapToResult
import com.barabasizsolt.mova.pager.RefreshType

class GetTopRatedMoviesUseCase(private val movieService: MovieService) {

    suspend operator fun invoke(refreshType: RefreshType) = wrapToResult {
        movieService.getTopRatedMovies(refreshType = refreshType).distinctBy { it.id }
    }
}