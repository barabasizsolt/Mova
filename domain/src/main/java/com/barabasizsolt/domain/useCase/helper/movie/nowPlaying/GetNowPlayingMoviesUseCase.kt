package com.barabasizsolt.domain.useCase.helper.movie.nowPlaying

import com.barabasizsolt.domain.util.wrapToResult
import com.barabasizsolt.movie.api.MovieService

class GetNowPlayingMoviesUseCase(private val movieService: MovieService) {

    suspend operator fun invoke(page: Int = 1) = wrapToResult { movieService.getNowPlayingMovies(page = page) }
}