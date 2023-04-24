package com.barabasizsolt.mova.shared

import androidx.compose.ui.window.ComposeUIViewController
import ui.navigation.appNav.AppNavigation
import ui.theme.MovaTheme

fun MainViewController() = ComposeUIViewController {
    MovaTheme {
        AppNavigation()
        //HomeScreen(screenState = rememberHomeScreenState())
    }
}