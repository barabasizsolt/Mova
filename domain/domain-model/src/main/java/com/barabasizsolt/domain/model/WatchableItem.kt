package com.barabasizsolt.domain.model

import com.barabasizsolt.movie.model.Movie
import com.barabasizsolt.people.model.People
import com.barabasizsolt.tv.modell.TvSeries

sealed class WatchableItem {

    abstract val id: String
    abstract val title: String
    abstract val voteAverage: String
    abstract val releaseDate: String
    abstract val posterPath: String

    data class Movie(
        override val id: String,
        override val title: String,
        override val voteAverage: String,
        override val releaseDate: String,
        override val posterPath: String
    ) : WatchableItem()

    data class TvSeries(
        override val id: String,
        override val title: String,
        override val voteAverage: String,
        override val releaseDate: String,
        override val posterPath: String
    ) : WatchableItem()

    data class People(
        override val id: String,
        override val title: String,
        override val posterPath: String,
        override val voteAverage: String = "",
        override val releaseDate: String = ""
    ) : WatchableItem()
}

fun Movie.toWatchableItem(): WatchableItem.Movie = WatchableItem.Movie(
    id = id,
    title = originalTitle,
    voteAverage = voteAverage,
    releaseDate = releaseDate,
    posterPath = posterPath.orEmpty()
)

fun TvSeries.toWatchableItem(): WatchableItem.TvSeries = WatchableItem.TvSeries(
    id = id,
    title = originalName,
    voteAverage = voteAverage,
    releaseDate = firstAirDate,
    posterPath = posterPath.orEmpty()
)

fun People.toWatchableItem(): WatchableItem.People = WatchableItem.People(
    id = id,
    title = name,
    posterPath = profilePath
)