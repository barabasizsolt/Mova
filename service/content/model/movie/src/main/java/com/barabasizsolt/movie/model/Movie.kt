package com.barabasizsolt.movie.model

import com.barabasizsolt.pagination.api.PagerItem

data class Movie(
    override val id: String,
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: List<Int>,
    val originalLanguage: String,
    val originalTitle: String,
    val title: String,
    val overview: String,
    val popularity: String,
    val posterPath: String?,
    val releaseDate: String,
    val voteAverage: String
) : PagerItem
