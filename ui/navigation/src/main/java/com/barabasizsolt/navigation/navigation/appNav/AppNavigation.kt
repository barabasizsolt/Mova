package com.barabasizsolt.navigation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.barabasizsolt.api.AuthenticationState
import com.barabasizsolt.navigation.navigation.appNav.rememberAppNavigationState
import com.barabasizsolt.navigation.navigation.bottomNav.BottomNavHolder

@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val appNavigationState = rememberAppNavigationState()

    Column {
        NavHost(
            navController = navController,
            startDestination = Route.Splash.route,
            builder = {
                composable(route = Route.Splash.route) {
                    /**TODO: Add some fancy splash screen*/
                    Spacer(modifier = Modifier.fillMaxSize())
                }
                authNavigation(navController = navController)
                mainNavigation(navController = navController)
            },
            modifier = Modifier.weight(weight = 1f)
        )
        BottomNavHolder(navController = navController, navBackStackEntry = navBackStackEntry)
    }

    LaunchedEffect(
        key1 = appNavigationState.authState,
        block = {
            when (appNavigationState.authState) {
                AuthenticationState.Logged -> navController.navigateToMain()
                AuthenticationState.NotLogged -> navController.navigateToAuth()
                else -> Unit
            }
        }
    )
}

fun NavHostController.navigateToAuth() {
    navigate(route = Route.Authentication.route) {
        popUpTo(id = this@navigateToAuth.graph.id) {
            inclusive = true
        }
    }
}

fun NavHostController.navigateToMain() {
    navigate(route = Route.Main.route) {
        popUpTo(id = this@navigateToMain.graph.id) {
            inclusive = true
        }
    }
}

fun NavHostController.navigateToSocialLogin() {
    navigate(route = Route.Authentication.SOCIAL_LOGIN)
}

fun NavHostController.navigateToAuthentication(screenType: String) {
    navigate(route = "Auth/${screenType}")
}