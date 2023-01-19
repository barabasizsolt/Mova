package com.barabasizsolt.people.model

import com.barabasizsolt.util.pagination.PagingItem

data class People(
    override val id: String,
    val adult: Boolean,
    val profilePath: String,
    val name: String
) : PagingItem