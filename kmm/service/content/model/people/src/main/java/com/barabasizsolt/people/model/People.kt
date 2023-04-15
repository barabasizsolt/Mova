package com.barabasizsolt.people.model

import com.barabasizsolt.pagination.PagerItem

data class People(
    override val id: String,
    val adult: Boolean,
    val profilePath: String,
    val name: String
) : PagerItem