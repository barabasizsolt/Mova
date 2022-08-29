package com.barabasizsolt.movie.model

data class MovieList(
    val page: Int,
    val results: List<Movie>
)
