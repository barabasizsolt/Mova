package com.barabasizsolt.domain.useCase.helper.movie.nowPlaying

import com.barabasizsolt.movie.api.MovieService
import kotlinx.coroutines.flow.filterNotNull

class GetNowPlayingMoviesFlowUseCase(private val movieService: MovieService) {

    operator fun invoke() = movieService.nowPlayingMovies.filterNotNull()
}