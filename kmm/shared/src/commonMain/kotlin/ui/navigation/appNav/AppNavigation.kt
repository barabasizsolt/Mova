package ui.navigation.appNav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.barabasizsolt.mova.auth.api.AuthenticationState
import ui.navigation.bottomNav.BottomNavScreen
import ui.screen.splash.SplashScreen
import ui.screen.welcome.WelcomeScreen

@Composable
internal fun AppNavigation() {
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
}