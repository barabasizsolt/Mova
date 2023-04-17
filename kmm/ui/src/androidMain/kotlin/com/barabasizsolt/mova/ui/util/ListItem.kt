package com.barabasizsolt.mova.ui.util

interface ListItem {

    val id: String

    override fun equals(other: Any?): Boolean

    override fun hashCode(): Int

}