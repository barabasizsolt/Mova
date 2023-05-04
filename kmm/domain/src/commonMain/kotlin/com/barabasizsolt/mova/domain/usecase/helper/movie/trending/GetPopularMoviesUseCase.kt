package com.barabasizsolt.mova.domain.usecase.helper.movie.trending

import com.barabasizsolt.mova.api.MovieService
import com.barabasizsolt.mova.domain.util.wrapToResult
import com.barabasizsolt.mova.pager.RefreshType

class GetPopularMoviesUseCase(private val movieService: MovieService) {

    suspend operator fun invoke(refreshType: RefreshType) = wrapToResult {
        movieService.getPopularMovies(refreshType = refreshType).distinctBy { it.id }
    }
}