package com.barabasizsolt.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.barabasizsolt.catalog.ErrorContent
import com.barabasizsolt.catalog.LoadingContent
import com.barabasizsolt.catalog.PeopleCarousel
import com.barabasizsolt.catalog.WatchablePager
import com.barabasizsolt.catalog.WatchableWithRatingCarousel
import com.barabasizsolt.theme.attributes.AppTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

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

        Scaffold(scaffoldState = scaffoldState) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = AppTheme.colors.primary)
                    .padding(paddingValues = it)
            ) {
                when (state) {
                    is HomeScreenModel.State.Error -> ErrorContent(onRetry = { screenModel.getScreenData(swipeRefresh = false) })
                    is HomeScreenModel.State.Loading -> LoadingContent()
                    else -> HomeContent(screenModel = screenModel, state = state)
                }

                LaunchedEffect(
                    key1 = state,
                    block = {
                        if (state is HomeScreenModel.State.ShowSnackBar) {
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = "Oops, something went wrong.",
                                actionLabel = "Try again"
                            )
                        }
                    }
                )
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