package com.barabasizsolt.mova.shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform