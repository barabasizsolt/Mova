package com.barabasizsolt.mova.ui.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.barabasizsolt.mova.ui.screen.main.explore.ExploreScreen
import com.barabasizsolt.mova.ui.screen.main.favourites.FavouritesScreen
import com.barabasizsolt.mova.ui.screen.main.home.HomeScreen
import com.barabasizsolt.mova.ui.screen.main.profile.ProfileScreen
import com.barabasizsolt.mova.ui.theme.AppTheme

object MainNavigation : Tab {
    override val options: TabOptions
        @Composable
        get() = remember { TabOptions(index = 10u, title = "main", icon = null) }

    @Composable
    override fun Content() {
        TabNavigator(tab = HomeScreen) {
            Scaffold(
                content = { CurrentTab() },
                bottomBar = {
                    BottomNavigation(
                        backgroundColor = AppTheme.colors.background,
                        content = {
                            TabNavigationItem(tab = HomeScreen)
                            TabNavigationItem(tab = ExploreScreen)
                            TabNavigationItem(tab = FavouritesScreen)
                            TabNavigationItem(tab = ProfileScreen)
                        }
                    )
                }
            )
        }
    }

}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    BottomNavigationItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title) },
        label = {
            Text(
                text = tab.options.title,
                style = AppTheme.typography.caption
            )
        },
        selectedContentColor = AppTheme.colors.secondary,
        unselectedContentColor = Color.Gray
    )
}
