package com.barabasizsolt.domain.model

import com.barabasizsolt.movie.model.Movie
import com.barabasizsolt.people.model.People
import com.barabasizsolt.tv.modell.TvSeries

data class WatchableItem(
    val id: String,
    val primaryInfo: String,
    val secondaryInfo: String,
    val posterPath: String
)

fun Movie.toWatchableItem(): WatchableItem = WatchableItem(
    id = id,
    primaryInfo = originalTitle,
    secondaryInfo = voteAverage,
    posterPath = posterPath
)

fun TvSeries.toWatchableItem(): WatchableItem = WatchableItem(
    id = id,
    primaryInfo = originalName,
    secondaryInfo = voteAverage,
    posterPath = posterPath
)

fun People.toWatchableItem(): WatchableItem = WatchableItem(
    id = id,
    primaryInfo = name,
    secondaryInfo = "",
    posterPath = profilePath
)
