package com.barabasizsolt.discover.api.model

data class TvSeries(
    val id: String,
    val backdropPath: String,
    val genreIds: List<String>,
    val originalCountry: List<String>,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val firstAirDate: String,
    val voteAverage: Double
)
