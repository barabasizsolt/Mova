package com.barabasizsolt.domain.model

import com.barabasizsolt.movie.model.Movie

data class HomeScreenContent(
    val upcomingMovies: List<Movie>,
    val popularMovies: List<WatchableItem>,
    val nowPlayingMovies: List<WatchableItem>,
    val topRatedMovies: List<WatchableItem>,
    val popularPeople: List<WatchableItem>
)