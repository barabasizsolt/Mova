package com.barabasizsolt.navigation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.barabasizsolt.login.LoginScreen

object AuthNavigation : Tab {
    override val options: TabOptions
        @Composable
        get() = remember { TabOptions(index = 1u, title = "auth", icon = null) }

    @Composable
    override fun Content() {
        Navigator(screen = LoginScreen)
    }
}