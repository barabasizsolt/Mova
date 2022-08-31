package com.barabasizsolt.mova.ui.screen.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.barabasizsolt.mova.R
import com.barabasizsolt.mova.ui.catalog.ErrorContent
import com.barabasizsolt.mova.ui.catalog.LoadingContent
import com.barabasizsolt.mova.ui.catalog.PeopleCarousel
import com.barabasizsolt.mova.ui.catalog.WatchableWithRatingCarousel
import com.barabasizsolt.mova.ui.catalog.WatchablePager
import com.barabasizsolt.mova.ui.theme.AppTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

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
        val state by screenModel.state.collectAsState()
        val scaffoldState = rememberScaffoldState()
        val coroutineScope = rememberCoroutineScope()

        Scaffold(scaffoldState = scaffoldState) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = AppTheme.colors.primary)
            ) {
                when (state) {
                    is HomeScreenModel.State.Error -> ErrorContent(onRetry = { screenModel.getScreenData(swipeRefresh = false) })
                    is HomeScreenModel.State.Loading -> LoadingContent()
                    else -> HomeContent(screenModel = screenModel, state = state)
                }

                when (state) {
                    is HomeScreenModel.State.ShowSnackBar -> coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "Oops, something went wrong.",
                            actionLabel = "Try again"
                        )
                    }
                    else -> Unit
                }
            }
        }
    }

    @Composable
    private fun HomeContent(
        screenModel: HomeScreenModel,
        state: HomeScreenModel.State
    ) {
        val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state is HomeScreenModel.State.SwipeRefresh)

        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = { screenModel.getScreenData(swipeRefresh = true) }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
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
                    WatchableWithRatingCarousel(
                        header = "Popular Movies",
                        buttonText = "More Popular Movies",
                        items = screenModel.homeContent?.trendingMovies.orEmpty(),
                        onItemClick = { },
                        onHeaderClick = { },
                    )
                }
                item {
                    PeopleCarousel(
                        header = "Popular People",
                        items = screenModel.homeContent?.popularPeople.orEmpty(),
                        onItemClick = { },
                        onHeaderClick = { },
                    )
                }
                item {
                    WatchableWithRatingCarousel(
                        header = "Now Playing Movies",
                        buttonText = "More Now Playing Movies",
                        items = screenModel.homeContent?.upcomingMovies.orEmpty(),
                        onItemClick = { },
                        onHeaderClick = { },
                    )
                }
                item {
                    WatchableWithRatingCarousel(
                        header = "Top Rated Movies",
                        buttonText = "More Top Rated Movies",
                        items = screenModel.homeContent?.topRatedMovies.orEmpty(),
                        onItemClick = { },
                        onHeaderClick = { },
                        showDivider = false
                    )
                }
            }
        }
    }
}