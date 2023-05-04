package ui.navigation.bottomNav

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
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

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        val movieListState: LazyGridState = rememberLazyGridState()
        val movieSearchState: LazyGridState = rememberLazyGridState()
        val tvListState: LazyGridState = rememberLazyGridState()
        val tvSearchState: LazyGridState = rememberLazyGridState()
        val movieGenreListState: LazyListState = rememberLazyListState()
        val tvGenreListState: LazyListState = rememberLazyListState()

        BottomSheetNavigator(
            sheetShape = AppTheme.shapes.medium.copy(
                bottomStart = CornerSize(size = 0.dp),
                bottomEnd = CornerSize(size = 0.dp)
            ),
            sheetContentColor = AppTheme.colors.onBackground,
            sheetBackgroundColor = AppTheme.colors.background
        ) {
            Navigator(
                screen = ExploreScreen(
                    movieListState = movieListState,
                    movieSearchState = movieSearchState,
                    tvListState = tvListState,
                    tvSearchState = tvSearchState,
                    movieGenreListState = movieGenreListState,
                    tvGenreListState = tvGenreListState
                )
            )
        }
    }
}