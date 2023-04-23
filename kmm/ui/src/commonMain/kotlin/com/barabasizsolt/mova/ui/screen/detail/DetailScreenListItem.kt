package com.barabasizsolt.mova.ui.screen.detail

import com.barabasizsolt.mova.domain.model.ContentItem
import com.barabasizsolt.mova.domain.model.DetailScreenContent
import com.barabasizsolt.mova.ui.util.ListItem
import movie.model.Movie
import review.model.Review
import video.model.Video

sealed class DetailScreenListItem : ListItem {

    data class HeaderItem(
        val title: String,
        val posterPath: String?,
        val backdropPath: String?,
        val genres: List<String>,
        val originalLanguage: String,
        val overview: String,
        val releaseDate: String,
        val spokenLanguages: List<String>,
        val status: String,
        val tagline: String,
        val voteAverage: String,
        val castCrew: List<ContentItem.Person>
    ) : DetailScreenListItem() {
        override val id: String = "headerItem"
    }

    data class TabsItem(
        val tabs: List<String>
    ) : DetailScreenListItem() {
        override val id: String = "tabsItem"
    }

    data class SimilarMovieItem(
        val movie: Movie,
        val movieIndex: Int,
        val isLast: Boolean
    ) : DetailScreenListItem() {
        override val id: String = "similarMovieItem_${movie.id}"
    }

    data class VideoItem(
        val video: Video,
        val isLast: Boolean
    ): DetailScreenListItem() {
        override val id: String = "videoItem_${video.id}"
    }

    data class ReviewItem(
        val review: Review,
        val isLast: Boolean
    ) : DetailScreenListItem() {
        override val id: String = "reviewItem_${review.id}"
    }

    data class EmptyItem(
        val nothing: Any? = null
    ): DetailScreenListItem() {
        override val id: String = "emptyItem"
    }
}

fun DetailScreenContent.MovieDetails.toHeaderItem(): DetailScreenListItem.HeaderItem = DetailScreenListItem.HeaderItem(
    title = title,
    posterPath = posterPath,
    backdropPath = backdropPath,
    genres = genres,
    originalLanguage = originalLanguage,
    overview = overview,
    releaseDate = releaseDate,
    spokenLanguages = spokenLanguages,
    status = status,
    tagline = tagline,
    voteAverage = voteAverage,
    castCrew = castCrew
)

fun DetailScreenContent.MovieDetails.toSimilarMoviesItem(): List<DetailScreenListItem> = if (similarMovies.isEmpty()) {
    listOf(DetailScreenListItem.EmptyItem())
} else {
    similarMovies.mapIndexed { index, movie ->
        DetailScreenListItem.SimilarMovieItem(
            movie = movie,
            movieIndex = similarMovies.indexOf(element = movie),
            isLast = index == similarMovies.lastIndex
        )
    }
}

fun DetailScreenContent.MovieDetails.toVideosItem(): List<DetailScreenListItem> = if (videos.isEmpty()) {
    listOf(DetailScreenListItem.EmptyItem())
} else {
    videos.mapIndexed { index, video ->
        DetailScreenListItem.VideoItem(
            video = video,
            isLast = index == videos.lastIndex
        )
    }
}

fun DetailScreenContent.MovieDetails.toReviewsItem(): List<DetailScreenListItem> = if (reviews.isEmpty()) {
    listOf(DetailScreenListItem.EmptyItem())
} else {
    reviews.mapIndexed { index, review ->
        DetailScreenListItem.ReviewItem(
            review = review,
            isLast = index == reviews.lastIndex
        )
    }
}