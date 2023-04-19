package com.barabasizsolt.mova.ui.navigation.bottomNav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.barabasizsolt.mova.ui.navigation.Route

val bottomItems = listOf(BottomNavItem.Home, BottomNavItem.Explore, BottomNavItem.Favourites, BottomNavItem.Profile)

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home: BottomNavItem(
        route = Route.Main.HOME,
        title = "Home",
        icon = Icons.Default.Home
    )
    object Explore: BottomNavItem(
        route = Route.Main.EXPLORE,
        title = "Explore",
        icon = Icons.Default.Search
    )
    object Favourites: BottomNavItem(
        route = Route.Main.FAVOURITES,
        title = "Favourites",
        icon = Icons.Default.Favorite
    )
    object Profile: BottomNavItem(
        route = Route.Main.PROFILE,
        title = "Profile",
        icon = Icons.Default.Person
    )
}