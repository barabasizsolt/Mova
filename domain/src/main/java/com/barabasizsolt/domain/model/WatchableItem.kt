package com.barabasizsolt.domain.model

import com.barabasizsolt.movie.model.Movie
import com.barabasizsolt.people.model.People
import com.barabasizsolt.tv.modell.TvSeries

data class WatchableItem(
    val id: String,
    val title: String,
    val voteAverage: String,
    val releaseDate: String,
    val posterPath: String
)

fun Movie.toWatchableItem(): WatchableItem = WatchableItem(
    id = id,
    title = originalTitle,
    voteAverage = voteAverage,
    releaseDate = releaseDate,
    posterPath = posterPath.orEmpty()
)

fun TvSeries.toWatchableItem(): WatchableItem = WatchableItem(
    id = id,
    title = originalName,
    voteAverage = voteAverage,
    releaseDate = firstAirDate,
    posterPath = posterPath.orEmpty()
)

fun People.toWatchableItem(): WatchableItem = WatchableItem(
    id = id,
    title = name,
    voteAverage = "",
    releaseDate = "",
    posterPath = profilePath
)
