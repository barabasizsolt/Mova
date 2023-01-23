package com.barabasizsolt.domain.model

import com.barabasizsolt.movie.model.Movie
import com.barabasizsolt.pagination.api.ErrorItem
import com.barabasizsolt.pagination.api.PagerItem
import com.barabasizsolt.pagination.api.TailItem
import com.barabasizsolt.people.model.People
import com.barabasizsolt.tv.modell.TvSeries

sealed class ContentItem {

    abstract val id: String

    data class Watchable(
        override val id: String,
        val posterPath: String,
        val title: String,
        val voteAverage: String,
        val releaseDate: String
    ) : ContentItem()

    data class Person(
        override val id: String,
        val name: String,
        val posterPath: String
    ) : ContentItem()

    data class ItemTail(
        override val id: String,
        val loadMore: Boolean
    ) : ContentItem()

    data class ItemError(
        override val id: String,
        val errorMessage: String = "Something went wrong."
    ) : ContentItem()
}

fun PagerItem.toContentItem(): ContentItem = when (this) {
    is TailItem -> ContentItem.ItemTail(
        id = id,
        loadMore = loadMore
    )
    is ErrorItem -> ContentItem.ItemError(
        id = id,
        errorMessage = errorMessage
    )
    is Movie -> ContentItem.Watchable(
        id = id,
        title = originalTitle,
        voteAverage = voteAverage,
        releaseDate = releaseDate,
        posterPath = posterPath.orEmpty()
    )
    is TvSeries -> ContentItem.Watchable(
        id = id,
        title = originalName,
        voteAverage = voteAverage,
        releaseDate = firstAirDate,
        posterPath = posterPath.orEmpty()
    )
    is People -> ContentItem.Person(
        id = id,
        name = name,
        posterPath = profilePath
    )
    else -> ContentItem.ItemTail(
        id = "unknown",
        loadMore = false
    )
}