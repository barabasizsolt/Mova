package com.barabasizsolt.filter.api

enum class SortOption(val value: String) {
    DEFAULT(value = "popularity.desc"),
    LATEST_RELEASE(value = "release_date.desc"),
    VOTE_AVERAGE(value = "vote_average.desc"),
}