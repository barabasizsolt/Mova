package com.barabasizsolt.mova.shared

import androidx.compose.ui.window.ComposeUIViewController
import com.barabasizsolt.mova.ui.navigation.appNav.AppNavigation
import com.barabasizsolt.mova.ui.theme.MovaTheme

fun MainViewController() = ComposeUIViewController {
    MovaTheme {
        AppNavigation()
    }
}