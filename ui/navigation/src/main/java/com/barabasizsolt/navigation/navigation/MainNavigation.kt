package com.barabasizsolt.navigation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.barabasizsolt.explore.ExploreScreen
import com.barabasizsolt.explore.rememberExploreScreenState
import com.barabasizsolt.favourites.FavouritesScreen
import com.barabasizsolt.home.HomeScreen
import com.barabasizsolt.home.rememberHomeScreenState
import com.barabasizsolt.profile.ProfileScreen

fun NavGraphBuilder.mainNavigation(navController: NavController) {
    navigation(
        startDestination = Route.Main.HOME,
        route = Route.Main.route
    ) {
        composable(route = Route.Main.HOME) {
            HomeScreen(screenState = rememberHomeScreenState())
        }

        composable(route = Route.Main.EXPLORE) {
            ExploreScreen(screenState = rememberExploreScreenState())
        }

        composable(route = Route.Main.FAVOURITES) {
            FavouritesScreen()
        }

        composable(route = Route.Main.PROFILE) {
            ProfileScreen()
        }
    }
}
