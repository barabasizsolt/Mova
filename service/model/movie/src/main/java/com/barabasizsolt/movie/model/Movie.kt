package com.barabasizsolt.movie.model

data class Movie(
    val id: String,
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: List<String>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double
)
