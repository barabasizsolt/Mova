package com.barabasizsolt.mova.ui.navigation.bottomNav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.barabasizsolt.mova.ui.screen.explore.ExploreScreen
import com.barabasizsolt.mova.ui.screen.home.HomeScreen

object HomeTab : Tab {

    override val options: TabOptions
        @Composable
        get()  {
            val icon = rememberVectorPainter(Icons.Default.Home)
            return remember {
                TabOptions(
                    index = 0u,
                    title = "Home",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(screen = HomeScreen)
    }

}

object ExploreTab : Tab {

    override val options: TabOptions
        @Composable
        get()  {
            val icon = rememberVectorPainter(Icons.Default.Search)
            return remember {
                TabOptions(
                    index = 1u,
                    title = "Explore",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(screen = ExploreScreen)
    }

}