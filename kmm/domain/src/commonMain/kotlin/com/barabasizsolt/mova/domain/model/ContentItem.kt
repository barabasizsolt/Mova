package com.barabasizsolt.mova.domain.model

import cast_crew.model.Cast
import cast_crew.model.Crew
import com.barabasizsolt.mova.pager.ErrorItem
import com.barabasizsolt.mova.pager.PagerItem
import com.barabasizsolt.mova.pager.TailItem
import movie.model.Movie
import people.model.People
import tv.modell.TvSeries

sealed class ContentItem {

    abstract val id: String

    data class Watchable(
        override val id: String,
        val posterPath: String,
        val title: String,
        val voteAverage: String,
        val releaseDate: String,
        val isMovie: Boolean
    ) : ContentItem()

    data class Person(
        override val id: String,
        val name: String,
        val posterPath: String,
        val knownForDepartment: String? = null,
        val job: String? = null
    ) : ContentItem()

    object ItemHeader : ContentItem() {
        override val id: String = "headerItem"
    }

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
        posterPath = posterPath.orEmpty(),
        isMovie = true
    )
    is TvSeries -> ContentItem.Watchable(
        id = id,
        title = originalName,
        voteAverage = voteAverage,
        releaseDate = firstAirDate,
        posterPath = posterPath.orEmpty(),
        isMovie = false
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

fun Cast.toContentItem() = ContentItem.Person(
    id = id,
    name = name,
    posterPath = profilePath.orEmpty(),
    knownForDepartment = knownForDepartment
)

fun Crew.toContentItem() = ContentItem.Person(
    id = id,
    name = name,
    posterPath = profilePath.orEmpty(),
    knownForDepartment = knownForDepartment,
    job = job
)