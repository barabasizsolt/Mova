package com.barabasizsolt.navigation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.barabasizsolt.auth.LoginScreen
import com.barabasizsolt.auth.AuthScreenState
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
                    is SocialLoginScreenState.Action.NavigateToHome -> {}
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

            LoginScreen(screenState = rememberAuthScreenState(screenType = screenType).apply {
                when (action?.consume()) {
                    is AuthScreenState.Action.NavigateToHome -> navController.navigateToMain()
                    is AuthScreenState.Action.NavigateToLogin -> navController.navigateToAuthentication(
                        screenType = ScreenType.LOGIN.name
                    )
                    is AuthScreenState.Action.NavigateToRegister -> navController.navigateToAuthentication(
                        screenType = ScreenType.REGISTER.name
                    )
                    else -> Unit
                }
            })
        }
    }
}