package ui.navigation.appNav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.barabasizsolt.api.AuthenticationState
import ui.screen.auth.welcome.WelcomeScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ui.navigation.bottomNav.BottomNavScreen
import ui.screen.splash.SplashScreen

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