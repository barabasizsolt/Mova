package com.barabasizsolt.mova.domain.usecase.helper.movie.nowPlaying

import com.barabasizsolt.mova.api.MovieService

class DeleteNowPlayingMoviesUseCase(private val movieService: MovieService) {

    operator fun invoke() = movieService.clearNowPlayingMovies()
}