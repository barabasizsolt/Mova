package com.barabasizsolt.mova.ui.navigation.appNav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.barabasizsolt.api.AuthenticationState
import com.barabasizsolt.mova.ui.navigation.bottomNav.BottomNavScreen
import com.barabasizsolt.mova.ui.screen.auth.welcome.WelcomeScreen
import com.barabasizsolt.mova.ui.screen.splash.SplashScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
actual fun AppNavigation() {
    val appNavigationState: AppNavigationState = rememberAppNavigationState()

    Navigator(screen = SplashScreen) { navigator ->
        LaunchedEffect(
            key1 = appNavigationState.authState,
            block = {
                navigator.popUntilRoot()
                when (appNavigationState.authState) {
                    AuthenticationState.Logged -> {
                        navigator.replace(item = BottomNavScreen)
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

@Composable
fun SetStatusBarColor() {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = Color.Transparent, darkIcons = false)
    systemUiController.setNavigationBarColor(color = Color.Transparent, darkIcons = false)
}