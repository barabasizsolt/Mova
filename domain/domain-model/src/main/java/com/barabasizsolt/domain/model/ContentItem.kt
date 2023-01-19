package com.barabasizsolt.domain.model

import com.barabasizsolt.movie.model.Movie
import com.barabasizsolt.people.model.People
import com.barabasizsolt.tv.modell.TvSeries
import com.barabasizsolt.util.PagingItem
import com.barabasizsolt.util.TailItem

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