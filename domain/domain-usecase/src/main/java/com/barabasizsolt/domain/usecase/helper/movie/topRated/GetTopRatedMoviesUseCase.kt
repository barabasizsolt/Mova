package com.barabasizsolt.domain.usecase.helper.movie.topRated

import com.barabasizsolt.domain.util.wrapToResult
import com.barabasizsolt.movie.api.MovieService
import com.barabasizsolt.util.RefreshType

class GetTopRatedMoviesUseCase(private val movieService: MovieService) {

    suspend operator fun invoke(refreshType: RefreshType) = wrapToResult { movieService.getTopRatedMovies(refreshType = refreshType) }
}