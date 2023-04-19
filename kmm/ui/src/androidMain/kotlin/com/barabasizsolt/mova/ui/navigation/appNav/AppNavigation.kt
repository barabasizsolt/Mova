package com.barabasizsolt.mova.ui.navigation.appNav

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.barabasizsolt.api.AuthenticationState
import com.barabasizsolt.mova.ui.navigation.Route
import com.barabasizsolt.mova.ui.navigation.authNavigation
import com.barabasizsolt.mova.ui.navigation.bottomNav.BottomNavHolder
import com.barabasizsolt.mova.ui.navigation.mainNavigation
import com.google.accompanist.systemuicontroller.rememberSystemUiController

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

    SetStatusBarColor()
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

@Composable
private fun SetStatusBarColor() {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = Color.Transparent, darkIcons = false)
    systemUiController.setNavigationBarColor(color = Color.Transparent, darkIcons = false)
}