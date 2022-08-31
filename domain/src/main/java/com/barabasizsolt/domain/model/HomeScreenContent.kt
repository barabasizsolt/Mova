package com.barabasizsolt.domain.model

import com.barabasizsolt.movie.model.Movie

data class HomeScreenContent(
    val upcomingMovies: List<WatchableItem>,
    val trendingMovies: List<WatchableItem>,
    val topRatedMovies: List<WatchableItem>,
    val nowPlayingMovies: List<Movie>
)
