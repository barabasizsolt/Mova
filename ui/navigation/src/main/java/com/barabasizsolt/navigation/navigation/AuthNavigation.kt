package com.barabasizsolt.navigation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.barabasizsolt.auth.LoginScreen
import com.barabasizsolt.auth.LoginScreenState
import com.barabasizsolt.auth.rememberLoginScreenState
import com.barabasizsolt.auth.socialLogin.SocialLoginScreen
import com.barabasizsolt.auth.socialLogin.SocialLoginScreenState
import com.barabasizsolt.auth.socialLogin.rememberSocialLoginScreenState
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
                    is WelcomeScreenState.Action.NavigateToAuth -> navController.navigateToSocialLogin()
                    else -> Unit
                }
            })
        }

        composable(route = Route.Authentication.SOCIAL_LOGIN) {
            SocialLoginScreen(screenState = rememberSocialLoginScreenState().apply {
                when (action?.consume()) {
                    is SocialLoginScreenState.Action.Authenticate -> {}
                    is SocialLoginScreenState.Action.NavigateToAuth -> navController.navigateToAuthentication()
                    else -> Unit
                }
            })
        }

        composable(route = Route.Authentication.AUTH) {
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