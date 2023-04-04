package com.barabasizsolt.domain.model

import com.barabasizsolt.movie.model.Movie
import com.barabasizsolt.review.model.Review
import com.barabasizsolt.video.model.Video

fun DetailScreenContent.MovieDetails.isEmpty(): Boolean = id == EMPTY

sealed class DetailScreenContent {

    abstract val id: String
    abstract val title: String
    abstract val posterPath: String?
    abstract val backdropPath: String?
    abstract val genres: List<String>
    abstract val originalLanguage: String
    abstract val overview: String
    abstract val releaseDate: String
    abstract val spokenLanguages: List<String>
    abstract val voteAverage: String

    data class MovieDetails(
        override val id: String,
        override val title: String,
        override val posterPath: String?,
        override val backdropPath: String?,
        override val genres: List<String>,
        override val originalLanguage: String,
        override val overview: String,
        override val releaseDate: String,
        override val spokenLanguages: List<String>,
        val status: String,
        val tagline: String,
        override val voteAverage: String,
        val videos: List<Video>,
        val similarMovies: List<Movie>,
        val reviews: List<Review>,
        val castCrew: List<ContentItem.Person>
    ) : DetailScreenContent() {
        companion object {
            fun createEmptyMovieDetailContent() = MovieDetails(
                id = EMPTY,
                title = EMPTY,
                posterPath = EMPTY,
                backdropPath = EMPTY,
                genres = emptyList(),
                originalLanguage = EMPTY,
                overview = EMPTY,
                releaseDate = EMPTY,
                spokenLanguages = emptyList(),
                status = EMPTY,
                tagline = EMPTY,
                voteAverage = EMPTY,
                videos = emptyList(),
                similarMovies = emptyList(),
                reviews = emptyList(),
                castCrew = emptyList()
            )
        }
    }

    data class TvDetails(
        override val id: String,
        override val title: String,
        override val posterPath: String?,
        override val backdropPath: String?,
        override val genres: List<String>,
        override val originalLanguage: String,
        override val overview: String,
        override val releaseDate: String,
        override val spokenLanguages: List<String>,
        override val voteAverage: String
    ) : DetailScreenContent()
}

private const val EMPTY: String = "empty"