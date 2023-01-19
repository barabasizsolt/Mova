package com.barabasizsolt.domain.model

import com.barabasizsolt.movie.model.Movie

data class HomeScreenContent(
    val upcomingMovies: List<Movie>,
    val popularMovies: List<ContentItem.Watchable>,
    val nowPlayingMovies: List<ContentItem.Watchable>,
    val topRatedMovies: List<ContentItem.Watchable>,
    val popularPeople: List<ContentItem.Person>
)