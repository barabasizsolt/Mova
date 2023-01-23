package com.barabasizsolt.domain.usecase.helper.movie.nowPlaying

import com.barabasizsolt.domain.util.result.wrapToResult
import com.barabasizsolt.movie.api.MovieService
import com.barabasizsolt.pagination.api.RefreshType

class GetNowPlayingMoviesUseCase(private val movieService: MovieService) {

    suspend operator fun invoke(refreshType: RefreshType) = wrapToResult {
        movieService.getNowPlayingMovies(refreshType = refreshType).distinctBy { it.id }
    }
}