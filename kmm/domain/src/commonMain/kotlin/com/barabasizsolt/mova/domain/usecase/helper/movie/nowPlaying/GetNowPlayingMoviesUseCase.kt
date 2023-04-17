package com.barabasizsolt.mova.domain.usecase.helper.movie.nowPlaying

import com.barabasizsolt.mova.api.MovieService
import com.barabasizsolt.mova.domain.util.wrapToResult
import com.barabasizsolt.mova.pager.RefreshType

class GetNowPlayingMoviesUseCase(private val movieService: MovieService) {

    suspend operator fun invoke(refreshType: RefreshType) = wrapToResult {
        movieService.getNowPlayingMovies(refreshType = refreshType).distinctBy { it.id }
    }
}