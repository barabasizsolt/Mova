package com.barabasizsolt.mova.domain.usecase.helper.movie.upcoming

import com.barabasizsolt.mova.api.MovieService
import com.barabasizsolt.mova.domain.util.wrapToResult
import com.barabasizsolt.mova.pager.RefreshType

class GetUpcomingMoviesUseCase(private val movieService: MovieService) {

    suspend operator fun invoke(refreshType: RefreshType) = wrapToResult {
        movieService.getUpcomingMovies(refreshType = refreshType).distinctBy { it.id }
    }
}