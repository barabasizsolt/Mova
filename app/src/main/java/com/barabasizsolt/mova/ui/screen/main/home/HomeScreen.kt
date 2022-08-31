package com.barabasizsolt.mova.ui.screen.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.barabasizsolt.mova.R
import com.barabasizsolt.mova.ui.catalog.CardCarousel
import com.barabasizsolt.mova.ui.catalog.WatchablePager
import com.barabasizsolt.mova.ui.theme.AppTheme

object HomeScreen : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(id = R.string.home_tab)
            val icon = rememberVectorPainter(image = Icons.Default.Home)
            return remember { TabOptions(index = 0u, title = title, icon = icon) }
        }

    @Composable
    override fun Content() {
        val screenModel = getScreenModel<HomeScreenModel>()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppTheme.colors.primary),
            verticalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.screenPadding),
            contentPadding = PaddingValues(bottom = AppTheme.dimens.screenPadding)
        ) {
            item {
                WatchablePager(
                    pagerContent = screenModel.homeContent?.nowPlayingMovies.orEmpty(),
                    onClick = { },
                    onPlayButtonClicked = { },
                    onAddToFavouriteButtonClicked = { }
                )
            }
            item {
                CardCarousel(
                    header = "Popular Movies",
                    items = screenModel.homeContent?.trendingMovies.orEmpty(),
                    onItemClick = { },
                    onHeaderClick = { },
                )
            }
            item {
                CardCarousel(
                    header = "Now Playing Movies",
                    items = screenModel.homeContent?.upcomingMovies.orEmpty(),
                    onItemClick = { },
                    onHeaderClick = { },
                )
            }
            item {
                CardCarousel(
                    header = "Top Rated Movies",
                    items = screenModel.homeContent?.topRatedMovies.orEmpty(),
                    onItemClick = { },
                    onHeaderClick = { },
                )
            }
        }
    }
}