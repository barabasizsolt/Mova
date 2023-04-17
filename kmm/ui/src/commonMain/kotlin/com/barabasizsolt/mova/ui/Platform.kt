package com.barabasizsolt.mova.ui

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform