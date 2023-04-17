package com.barabasizsolt.mova.domain.model

import movie.model.Movie

fun HomeScreenContent.isEmpty(): Boolean =
    upcomingMovies.isEmpty() ||
            popularMovies.isEmpty() ||
            nowPlayingMovies.isEmpty() ||
            topRatedMovies.isEmpty() ||
            popularPeople.isEmpty() ||
            genres.isEmpty()

data class HomeScreenContent(
    val upcomingMovies: List<Movie>,
    val popularMovies: List<ContentItem.Watchable>,
    val nowPlayingMovies: List<ContentItem.Watchable>,
    val topRatedMovies: List<ContentItem.Watchable>,
    val popularPeople: List<ContentItem.Person>,
    val genres: Map<Long, String>
) {
    companion object {
        fun createEmptyHomeScreenContent() = HomeScreenContent(
            upcomingMovies = emptyList(),
            popularPeople = emptyList(),
            nowPlayingMovies = emptyList(),
            topRatedMovies = emptyList(),
            popularMovies = emptyList(),
            genres = emptyMap()
        )
    }
}