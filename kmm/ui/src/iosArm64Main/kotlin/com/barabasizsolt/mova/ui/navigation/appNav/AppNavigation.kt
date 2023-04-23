package com.barabasizsolt.mova.ui.navigation.appNav

import androidx.compose.runtime.Composable
import com.barabasizsolt.mova.ui.navigation.bottomNav.BottomNavScreen

@Composable
actual fun AppNavigation() {
    Navigator(screen = BottomNavScreen)
}