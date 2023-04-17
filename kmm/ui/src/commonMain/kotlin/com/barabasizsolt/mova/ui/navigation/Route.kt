package com.barabasizsolt.mova.ui.navigation

sealed class Route {
    abstract val route: String

    object Splash : Route() {
        override val route: String = "Splash"
    }

    object Authentication : Route() {
        override val route: String = "Authentication"
        const val WELCOME: String = "Welcome"
        const val SOCIAL_LOGIN: String = "SocialLogin"
        const val AUTH: String = "Auth/{screenType}"
    }

    object Main : Route() {
        override val route: String = "Main"
        const val HOME: String = "Home"
        const val EXPLORE: String = "Explore"
        const val FAVOURITES: String = "Favourites"
        const val PROFILE: String = "Profile"
        const val SEE_ALL: String = "SeeAll/{contentType}"
        const val DETAIL: String = "Detail/{id}"
    }
}