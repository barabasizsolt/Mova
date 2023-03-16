package com.barabasizsolt.domain.model

import com.barabasizsolt.cast_crew.model.CastCrew
import com.barabasizsolt.movie.model.Movie
import com.barabasizsolt.review.model.Review
import com.barabasizsolt.video.model.Video

sealed class DetailScreenContent {

    abstract val id: String

    data class MovieDetails(
        override val id: String,
        val title: String,
        val posterPath: String?,
        val backdropPath: String?,
        val genres: Map<Long, String>,
        val originalLanguage: String,
        val overview: String,
        val releaseDate: String,
        val spokenLanguages: List<String>,
        val status: String,
        val tagline: String,
        val voteAverage: String,
        val videos: List<Video>,
        val similarMovies: List<Movie>,
        val reviews: List<Review>,
        val castCrew: CastCrew
    ) : DetailScreenContent()

    data class TvDetails(
        override val id: String
    ) : DetailScreenContent()
}