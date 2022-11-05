package com.barabasizsolt.domain.usecase.helper.movie.nowPlaying

import com.barabasizsolt.domain.util.wrapToResult
import com.barabasizsolt.movie.api.MovieService
import com.barabasizsolt.movie.api.RefreshType

class GetNowPlayingMoviesUseCase(private val movieService: MovieService) {

    suspend operator fun invoke(refreshType: RefreshType) = wrapToResult { movieService.getNowPlayingMovies(refreshType = refreshType) }
}