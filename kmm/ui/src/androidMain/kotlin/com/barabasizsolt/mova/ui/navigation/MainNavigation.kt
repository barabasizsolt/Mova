package com.barabasizsolt.mova.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.barabasizsolt.mova.ui.screen.detail.DetailScreen
import com.barabasizsolt.mova.ui.screen.detail.DetailScreenState
import com.barabasizsolt.mova.ui.screen.detail.rememberDetailScreenState
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.mainNavigation(navController: NavHostController) {
    navigation(
        startDestination = Route.Main.HOME,
        route = Route.Main.route
    ) {
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

fun NavHostController.navigateToDetails(id: Int) {
    navigate(route = "Detail/${id}")
}
