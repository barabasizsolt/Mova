package com.barabasizsolt.domain.model

import com.barabasizsolt.movie.model.Movie

data class HomeScreenContent(
    val trendingMovies: List<Movie>,
    val upcomingMovies: List<Movie>
)
