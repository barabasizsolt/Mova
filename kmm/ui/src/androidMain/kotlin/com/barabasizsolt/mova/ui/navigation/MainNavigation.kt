package com.barabasizsolt.mova.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.barabasizsolt.mova.domain.usecase.screen.seeall.SeeAllContentType
import com.barabasizsolt.mova.ui.screen.detail.DetailScreen
import com.barabasizsolt.mova.ui.screen.detail.DetailScreenState
import com.barabasizsolt.mova.ui.screen.detail.rememberDetailScreenState
import com.barabasizsolt.mova.ui.screen.explore.ExploreScreen
import com.barabasizsolt.mova.ui.screen.explore.ExploreScreenState
import com.barabasizsolt.mova.ui.screen.explore.rememberExploreScreenState
import com.barabasizsolt.mova.ui.screen.favourite.FavouritesScreen
import com.barabasizsolt.mova.ui.screen.home.HomeScreen
import com.barabasizsolt.mova.ui.screen.home.HomeScreenState
import com.barabasizsolt.mova.ui.screen.home.rememberHomeScreenState
import com.barabasizsolt.mova.ui.screen.profile.ProfileScreen
import com.barabasizsolt.mova.ui.screen.seeall.SeeAllScreen
import com.barabasizsolt.mova.ui.screen.seeall.SeeAllScreenState
import com.barabasizsolt.mova.ui.screen.seeall.rememberSeeAllScreenState
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.mainNavigation(navController: NavHostController) {
    navigation(
        startDestination = Route.Main.HOME,
        route = Route.Main.route
    ) {
        composable(route = Route.Main.HOME) {
            HomeScreen(screenState = rememberHomeScreenState().apply {
                when (val action = action?.consume()) {
                    is HomeScreenState.Action.SeeAllNowPlayingMovies ->
                        navController.navigateToSeeAll(contentType = SeeAllContentType.NOW_PLAYING_MOVIES.name)
                    is HomeScreenState.Action.SeeAllPopularMovies ->
                        navController.navigateToSeeAll(contentType = SeeAllContentType.POPULAR_MOVIES.name)
                    is HomeScreenState.Action.SeeAllPopularPeople ->
                        navController.navigateToSeeAll(contentType = SeeAllContentType.POPULAR_PEOPLE.name)
                    is HomeScreenState.Action.SeeAllTopRatedMovies ->
                        navController.navigateToSeeAll(contentType = SeeAllContentType.TOP_RATED_MOVIES.name)
                    is HomeScreenState.Action.OnMovieClicked ->
                        navController.navigateToDetails(id = action.id)
                    else -> Unit
                }
            })
        }

        composable(route = Route.Main.EXPLORE) {
            ExploreScreen(screenState = rememberExploreScreenState().apply {
                when (val action = action?.consume()) {
                    is ExploreScreenState.Action.OnMovieClicked ->
                        navController.navigateToDetails(id = action.id)
                    else -> Unit
                }
            })
        }

        composable(route = Route.Main.FAVOURITES) {
            FavouritesScreen()
        }

        composable(route = Route.Main.PROFILE) {
            ProfileScreen()
        }

        composable(route = Route.Main.SEE_ALL) { backstackEntry ->
            val contentType = backstackEntry.arguments?.getString("contentType") as String

            SeeAllScreen(screenState = rememberSeeAllScreenState(contentType = contentType).apply {
                when (val action = action?.consume()) {
                    is SeeAllScreenState.Action.NavigateUp ->
                        navController.navigateUp()
                    is SeeAllScreenState.Action.OnMovieClicked ->
                        navController.navigateToDetails(id = action.id)
                    else -> Unit
                }
            })
        }

        composable(
            route = Route.Main.DETAIL,
            arguments = listOf(navArgument(name = "id") { type = NavType.IntType })
        ) { backstackEntry ->
            val id = backstackEntry.arguments?.getInt("id") as Int

            DetailScreen(screenState = rememberDetailScreenState(id = id).apply {
                when (val action = action?.consume()) {
                    is DetailScreenState.Action.OnMovieClicked ->
                        navController.navigateToDetails(id = action.id)
                    else -> Unit
                }
            })
        }
    }
}

fun NavHostController.navigateToSeeAll(contentType: String) {
    navigate(route = "SeeAll/${contentType}")
}

fun NavHostController.navigateToDetails(id: Int) {
    navigate(route = "Detail/${id}")
}
