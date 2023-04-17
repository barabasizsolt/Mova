package com.barabasizsolt.mova.domain.usecase.helper.movie.nowPlaying

import com.barabasizsolt.mova.api.MovieService
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class GetNowPlayingMoviesFlowUseCase(private val movieService: MovieService) {

    operator fun invoke() = movieService.nowPlayingMovies.filterNotNull().map { it.distinctBy { movie -> movie.id } }
}