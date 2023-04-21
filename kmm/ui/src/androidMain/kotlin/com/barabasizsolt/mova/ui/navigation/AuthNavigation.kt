package com.barabasizsolt.mova.ui.navigation

import androidx.navigation.NavHostController

//@OptIn(ExperimentalAnimationApi::class)
//fun NavGraphBuilder.authNavigation(navController: NavHostController) {
//    navigation(
//        startDestination = Route.Authentication.WELCOME,
//        route = Route.Authentication.route
//    ) {
//        composable(route = Route.Authentication.WELCOME) {
//            WelcomeScreen(screenState = rememberWelcomeScreenState().apply {
//                when (action?.consume()) {
//                    is WelcomeScreenState.Action.NavigateToAuth -> navController.navigateToSocialLogin()
//                    else -> Unit
//                }
//            })
//        }
//
//        composable(route = Route.Authentication.SOCIAL_LOGIN) {
//            SocialLoginScreen(screenState = rememberSocialLoginScreenState().apply {
//                when (action?.consume()) {
//                    is SocialLoginScreenState.Action.NavigateToLogin -> navController.navigateToAuthentication(
//                        screenType = ScreenType.LOGIN.name
//                    )
//                    is SocialLoginScreenState.Action.NavigateToRegister -> navController.navigateToAuthentication(
//                        screenType = ScreenType.REGISTER.name
//                    )
//                    else -> Unit
//                }
//            })
//        }
//
//        composable(route = Route.Authentication.AUTH) { backstackEntry ->
//            val screenType = backstackEntry.arguments?.getString("screenType") as String
//
//            AuthScreen(screenState = rememberAuthScreenState(screenType = screenType))
//        }
//    }
//}

fun NavHostController.navigateToSocialLogin() {
    navigate(route = Route.Authentication.SOCIAL_LOGIN)
}

fun NavHostController.navigateToAuthentication(screenType: String) {
    navigate(route = "Auth/${screenType}") {
        launchSingleTop = true
    }
}