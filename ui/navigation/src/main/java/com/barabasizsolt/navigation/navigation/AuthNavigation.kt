package com.barabasizsolt.navigation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.barabasizsolt.login.LoginScreen
import com.barabasizsolt.login.LoginScreenState
import com.barabasizsolt.login.rememberLoginScreenState
import com.barabasizsolt.welcome.WelcomeScreen
import com.barabasizsolt.welcome.WelcomeScreenState
import com.barabasizsolt.welcome.rememberWelcomeScreenState

fun NavGraphBuilder.authNavigation(navController: NavHostController) {
    navigation(
        startDestination = Route.Authentication.WELCOME,
        route = Route.Authentication.route
    ) {
        composable(route = Route.Authentication.WELCOME) {
            WelcomeScreen(screenState = rememberWelcomeScreenState().apply {
                when (action?.consume()) {
                    is WelcomeScreenState.Action.NavigateToLogin -> navController.navigateToLogin()
                    else -> Unit
                }
            })
        }

        composable(route = Route.Authentication.LOGIN) {
            LoginScreen(screenState = rememberLoginScreenState().apply {
                when (action?.consume()) {
                    is LoginScreenState.Action.NavigateToHome -> navController.navigateToMain()
                    is LoginScreenState.Action.NavigateToRegister -> {
                        //TODO: register screen
                    }
                    else -> Unit
                }
            })
        }
    }
}