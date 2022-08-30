package com.barabasizsolt.mova.util

private val basePath: String = "https://image.tmdb.org/t/p"

fun String.getImageKey(size: Int? = null) = if (size != null) "$basePath/w$size/$this" else "$basePath/original/$this"

fun String.isSvg(): Boolean = this.takeLast(n = 3) == "svg"