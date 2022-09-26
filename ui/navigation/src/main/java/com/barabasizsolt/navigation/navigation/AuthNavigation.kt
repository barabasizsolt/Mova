package com.barabasizsolt.navigation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.barabasizsolt.login.LoginScreen
import com.barabasizsolt.login.LoginScreenState
import com.barabasizsolt.login.rememberLoginScreenState

fun NavGraphBuilder.authNavigation(navController: NavController) {
    navigation(
        startDestination = Route.Authentication.LOGIN,
        route = Route.Authentication.route
    ) {
        composable(route = Route.Authentication.LOGIN) {
            LoginScreen(screenState = rememberLoginScreenState().apply {
                when(action?.consume()) {
                    is LoginScreenState.Action.NavigateToHome ->
                        navController.navigate(route = Route.Main.route)
                    is LoginScreenState.Action.NavigateToRegister -> {
                        //TODO: register screen
                    }
                    else -> Unit
                }
            })
        }
    }
}