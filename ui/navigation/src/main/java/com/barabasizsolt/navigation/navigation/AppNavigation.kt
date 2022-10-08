package com.barabasizsolt.navigation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.barabasizsolt.domain.usecase.auth.IsLoggedInUseCase
import com.barabasizsolt.navigation.navigation.bottomNav.BottomNavHolder
import org.koin.androidx.compose.inject

@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Column {
        NavHost(
            navController = navController,
            startDestination = Route.Splash.route,
            builder = {
                composable(route = Route.Splash.route) {
                    val isLoggedInUseCase by inject<IsLoggedInUseCase>()
                    LaunchedEffect(
                        key1 = Unit,
                        block = {
                            if (isLoggedInUseCase()) navController.navigateToMain() else navController.navigateToAuth()
                        }
                    )
                }
                authNavigation(navController = navController)
                mainNavigation(navController = navController)
            },
            modifier = Modifier.weight(weight = 1f)
        )
        BottomNavHolder(navController = navController, navBackStackEntry = navBackStackEntry)
    }
}

fun NavHostController.navigateToAuth(popUpToRoute: String = Route.Splash.route) {
    navigate(route = Route.Authentication.route) {
        launchSingleTop = true
        popUpTo(route = popUpToRoute) {
            inclusive = true
        }
    }
}

fun NavHostController.navigateToMain(popUpToRoute: String = Route.Splash.route) {
    navigate(route = Route.Main.route) {
        launchSingleTop = true
        popUpTo(route = popUpToRoute) {
            inclusive = true
        }
    }
}

fun NavHostController.navigateToHome(popUpToRoute: String = Route.Authentication.route) {
    navigate(route = Route.Main.HOME) {
        launchSingleTop = true
        popUpTo(route = popUpToRoute) {
            inclusive = true
        }
    }
}