package com.barabasizsolt.navigation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.barabasizsolt.explore.ExploreScreen
import com.barabasizsolt.favourites.FavouritesScreen
import com.barabasizsolt.home.HomeScreen
import com.barabasizsolt.profile.ProfileScreen
import com.barabasizsolt.theme.attributes.AppTheme
import com.barabasizsolt.util.navigationBarInsetDp

object MainNavigation : Tab {
    override val options: TabOptions
        @Composable
        get() = remember { TabOptions(index = 2u, title = "main", icon = null) }

    @Composable
    override fun Content() {
        TabNavigator(tab = HomeScreen) {
            Column {
                Box(modifier = Modifier.weight(weight = 1f)) {
                    CurrentTab()
                }
                BottomNavigation(
                    backgroundColor = AppTheme.colors.background,
                    content = {
                        TabNavigationItem(tab = HomeScreen)
                        TabNavigationItem(tab = ExploreScreen)
                        TabNavigationItem(tab = FavouritesScreen)
                        TabNavigationItem(tab = ProfileScreen)
                    },
                    modifier = Modifier.height(height = navigationBarInsetDp + AppTheme.dimens.bottomNavHeight)
                )
            }
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
        unselectedContentColor = Color.Gray,
        modifier = Modifier
            .systemBarsPadding()
    )
}
