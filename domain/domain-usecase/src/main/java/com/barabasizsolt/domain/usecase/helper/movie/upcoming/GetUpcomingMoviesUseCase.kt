package com.barabasizsolt.domain.usecase.helper.movie.upcoming

import com.barabasizsolt.domain.util.wrapToResult
import com.barabasizsolt.movie.api.MovieService
import com.barabasizsolt.util.RefreshType

class GetUpcomingMoviesUseCase(private val movieService: MovieService) {

    suspend operator fun invoke(refreshType: RefreshType) = wrapToResult {
        movieService.getUpcomingMovies(refreshType = refreshType).distinctBy { it.id }
    }
}