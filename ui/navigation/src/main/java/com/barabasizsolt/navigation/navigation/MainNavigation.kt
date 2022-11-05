package com.barabasizsolt.navigation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.barabasizsolt.explore.ExploreScreen
import com.barabasizsolt.explore.rememberExploreScreenState
import com.barabasizsolt.favourites.FavouritesScreen
import com.barabasizsolt.home.HomeScreen
import com.barabasizsolt.home.HomeScreenState
import com.barabasizsolt.home.rememberHomeScreenState
import com.barabasizsolt.profile.ProfileScreen
import com.barabasizsolt.domain.usecase.screen.seeall.SeeAllContentType
import com.barabasizsolt.seeall.SeeAllScreen
import com.barabasizsolt.seeall.rememberSeeAllScreenState

fun NavGraphBuilder.mainNavigation(navController: NavHostController) {
    navigation(
        startDestination = Route.Main.HOME,
        route = Route.Main.route
    ) {
        composable(route = Route.Main.HOME) {
            HomeScreen(screenState = rememberHomeScreenState().apply {
                when (action?.consume()) {
                    is HomeScreenState.Action.SeeAllNowPlayingMovies ->
                        navController.navigateToSeeAll(contentType = SeeAllContentType.NOW_PLAYING_MOVIES.name)
                    is HomeScreenState.Action.SeeAllPopularMovies ->
                        navController.navigateToSeeAll(contentType = SeeAllContentType.POPULAR_MOVIES.name)
                    is HomeScreenState.Action.SeeAllPopularPeople ->
                        navController.navigateToSeeAll(contentType = SeeAllContentType.POPULAR_PEOPLE.name)
                    is HomeScreenState.Action.SeeAllTopRatedMovies ->
                        navController.navigateToSeeAll(contentType = SeeAllContentType.TOP_RATED_MOVIES.name)
                    else -> Unit
                }
            })
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

        composable(route = Route.Main.SEE_ALL) { backstackEntry ->
            val contentType = backstackEntry.arguments?.getString("contentType") as String

            SeeAllScreen(screenState = rememberSeeAllScreenState(contentType = contentType))
        }
    }
}

fun NavHostController.navigateToSeeAll(contentType: String) {
    navigate(route = "SeeAll/${contentType}")
}
