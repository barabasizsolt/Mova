package com.barabasizsolt.mova.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.barabasizsolt.mova.ui.screen.auth.login.LoginScreen

object AuthNavigation : Tab {
    override val options: TabOptions
        @Composable
        get() = remember { TabOptions(index = 11u, title = "auth", icon = null) }

    @Composable
    override fun Content() {
        Navigator(screen = LoginScreen)
    }
}