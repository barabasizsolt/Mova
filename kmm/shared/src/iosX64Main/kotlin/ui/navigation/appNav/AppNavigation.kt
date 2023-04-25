package ui.navigation.appNav

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import ui.navigation.bottomNav.BottomNavScreen

@Composable
actual fun AppNavigation() {
    Navigator(screen = BottomNavScreen)
}