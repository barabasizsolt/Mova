package com.barabasizsolt.navigation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.barabasizsolt.auth.AuthScreen
import com.barabasizsolt.auth.ScreenType
import com.barabasizsolt.auth.rememberAuthScreenState
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
                    is SocialLoginScreenState.Action.NavigateToLogin -> navController.navigateToAuthentication(
                        screenType = ScreenType.LOGIN.name
                    )
                    is SocialLoginScreenState.Action.NavigateToRegister -> navController.navigateToAuthentication(
                        screenType = ScreenType.REGISTER.name
                    )
                    else -> Unit
                }
            })
        }

        composable(route = Route.Authentication.AUTH) { backstackEntry ->
            val screenType = backstackEntry.arguments?.getString("screenType") as String

            AuthScreen(screenState = rememberAuthScreenState(screenType = screenType))
        }
    }
}

fun NavHostController.navigateToSocialLogin() {
    navigate(route = Route.Authentication.SOCIAL_LOGIN)
}

fun NavHostController.navigateToAuthentication(screenType: String) {
    navigate(route = "Auth/${screenType}") {
        launchSingleTop = true
    }
}