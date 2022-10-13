package com.barabasizsolt.navigation.navigation

sealed class Route {
    abstract val route: String

    object Splash : Route() {
        override val route: String = "Splash"
    }

    object Authentication : Route() {
        override val route: String = "Authentication"
        const val WELCOME: String = "Welcome"
        const val LOGIN: String = "login"
    }

    object Main : Route() {
        override val route: String = "Main"
        const val HOME: String = "home"
        const val EXPLORE: String = "explore"
        const val FAVOURITES: String = "favourites"
        const val PROFILE: String = "profile"
    }
}