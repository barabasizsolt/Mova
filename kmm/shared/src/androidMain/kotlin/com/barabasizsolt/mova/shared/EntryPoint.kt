package com.barabasizsolt.mova.shared

import androidx.compose.runtime.Composable
import com.barabasizsolt.mova.ui.navigation.appNav.AppNavigation
import com.barabasizsolt.mova.ui.theme.MovaTheme

@Composable
actual fun EntryPoint() {
    MovaTheme {
        AppNavigation()
    }
}