package com.barabasizsolt.domain.model

import com.barabasizsolt.movie.model.Movie
import com.barabasizsolt.tv.modell.TvSeries

data class WatchableItem(
    val id: String,
    val title: String,
    val rating: String,
    val posterPath: String
)

fun Movie.toWatchableItem(): WatchableItem = WatchableItem(
    id = id,
    title = originalTitle,
    rating = voteAverage,
    posterPath = posterPath
)

fun TvSeries.toWatchableItem(): WatchableItem = WatchableItem(
    id = id,
    title = originalName,
    rating = voteAverage,
    posterPath = posterPath
)
