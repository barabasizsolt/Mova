package com.barabasizsolt.domain.usecase.helper.movie.nowPlaying

import com.barabasizsolt.movie.api.MovieService

class DeleteNowPlayingMoviesUseCase(private val movieService: MovieService) {

    operator fun invoke() = movieService.clearNowPlayingMovies()
}