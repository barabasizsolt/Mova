package com.barabasizsolt.domain.model

import com.barabasizsolt.movie.model.Movie
import com.barabasizsolt.pagination.api.ErrorItem
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
        override val id: String = "itemError",
        val errorMessage: String = "Something went wrong."
    ) : ContentItem()
}

fun Movie.toContentItem(): ContentItem.Watchable = ContentItem.Watchable(
    id = id,
    title = originalTitle,
    voteAverage = voteAverage,
    releaseDate = releaseDate,
    posterPath = posterPath.orEmpty()
)

fun TvSeries.toContentItem(): ContentItem.Watchable = ContentItem.Watchable(
    id = id,
    title = originalName,
    voteAverage = voteAverage,
    releaseDate = firstAirDate,
    posterPath = posterPath.orEmpty()
)

fun People.toContentItem(): ContentItem.Person = ContentItem.Person(
    id = id,
    name = name,
    posterPath = profilePath
)

fun TailItem.toContentItem(): ContentItem.ItemTail = ContentItem.ItemTail(
    id = id,
    loadMore = loadMore
)

fun ErrorItem.toContentItem(): ContentItem.ItemError = ContentItem.ItemError(
    id = id,
    errorMessage = errorMessage
)