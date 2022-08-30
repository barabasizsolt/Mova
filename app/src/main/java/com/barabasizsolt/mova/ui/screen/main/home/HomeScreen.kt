package com.barabasizsolt.mova.ui.screen.main.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getScreenModel

import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.barabasizsolt.mova.R
import com.barabasizsolt.mova.ui.catalog.CardCarousel
import com.barabasizsolt.mova.ui.catalog.CardWithRating

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
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(space = 20.dp)
        ) {
            item {
                CardCarousel(
                    header = "Most Searched Movies",
                    items = screenModel.homeContent?.trendingMovies.orEmpty(),
                    onItemClick = { },
                    onHeaderClick = { },
                )
            }
            item {
                CardCarousel(
                    header = "Upcoming Movies",
                    items = screenModel.homeContent?.upcomingMovies.orEmpty(),
                    onItemClick = { },
                    onHeaderClick = { },
                )
            }
        }
    }
}