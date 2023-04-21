package com.barabasizsolt.mova.ui.navigation.appNav

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.barabasizsolt.api.AuthenticationState
import com.barabasizsolt.mova.ui.navigation.Route
import com.barabasizsolt.mova.ui.navigation.bottomNav.BottomNavHolder
import com.barabasizsolt.mova.ui.navigation.mainNavigation
import com.barabasizsolt.mova.ui.screen.auth.welcome.WelcomeScreen
import com.barabasizsolt.mova.ui.screen.profile.ProfileScreen
import com.barabasizsolt.mova.ui.screen.splash.SplashScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.accompanist.navigation.animation.composable

@Composable
fun TemporaryAppNavigation() {
    val appNavigationState: AppNavigationState = rememberAppNavigationState()

    Navigator(SplashScreen) { navigator ->
        LaunchedEffect(
            key1 = appNavigationState.authState,
            block = {
                navigator.popUntilRoot()
                when (appNavigationState.authState) {
                    AuthenticationState.Logged -> {
                        navigator.replace(item = ProfileScreen)
                    }
                    AuthenticationState.NotLogged -> {
                        navigator.replace(item = WelcomeScreen)
                    }
                    else -> Unit
                }
            }
        )
        CurrentScreen()
    }
    SetStatusBarColor()
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberAnimatedNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val appNavigationState = rememberAppNavigationState()

    Column {
        AnimatedNavHost(
            navController = navController,
            startDestination = Route.Main.route,
            builder = {
                composable(route = Route.Splash.route) {
                    Spacer(modifier = Modifier.fillMaxSize())
                }
                //authNavigation(navController = navController)
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