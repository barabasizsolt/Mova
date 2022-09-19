package com.barabasizsolt.people.model

data class PeopleList(
    val page: Int,
    val results: List<People>
)