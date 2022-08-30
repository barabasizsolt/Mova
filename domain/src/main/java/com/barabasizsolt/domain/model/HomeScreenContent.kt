package com.barabasizsolt.domain.model

data class HomeScreenContent(
    val trendingMovies: List<WatchableItem>,
    val upcomingMovies: List<WatchableItem>
)
