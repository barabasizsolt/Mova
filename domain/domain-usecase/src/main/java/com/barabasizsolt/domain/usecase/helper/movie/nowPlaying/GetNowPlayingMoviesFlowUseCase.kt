package com.barabasizsolt.domain.usecase.helper.movie.nowPlaying

import com.barabasizsolt.movie.api.MovieService
import kotlinx.coroutines.flow.filterNotNull

class GetNowPlayingMoviesFlowUseCase(private val movieService: MovieService) {

    operator fun invoke() = movieService.nowPlayingMovies.filterNotNull()
}