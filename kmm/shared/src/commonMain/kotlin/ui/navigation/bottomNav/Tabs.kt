package ui.navigation.bottomNav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import ui.screen.explore.ExploreScreen
import ui.screen.home.HomeScreen
import ui.theme.AppTheme

internal object HomeTab : Tab {

    override val options: TabOptions
        @Composable
        get()  {
            val icon = rememberVectorPainter(Icons.Default.Home)
            val title = AppTheme.strings.home
            return remember { TabOptions(index = 0u, title = title, icon = icon) }
        }

    @Composable
    override fun Content() {
        Navigator(screen = HomeScreen)
    }
}

internal object ExploreTab : Tab {

    override val options: TabOptions
        @Composable
        get()  {
            val icon = rememberVectorPainter(Icons.Default.Search)
            val title = AppTheme.strings.explore
            return remember { TabOptions(index = 1u, title = title, icon = icon) }
        }

    @Composable
    override fun Content() {
        Navigator(screen = ExploreScreen)
    }
}