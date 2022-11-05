package com.barabasizsolt.seeall

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.barabasizsolt.catalog.ErrorContent
import com.barabasizsolt.catalog.LoadingContent
import com.barabasizsolt.catalog.WatchableWithRating
import com.barabasizsolt.domain.model.WatchableItem
import com.barabasizsolt.theme.attributes.AppTheme
import com.barabasizsolt.util.imeBottomInsetDp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun SeeAllScreen(screenState: SeeAllScreenState) {
    val scaffoldState = rememberScaffoldState()
    println("State: ${screenState.state}")

    Scaffold(scaffoldState = scaffoldState) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppTheme.colors.primary)
                .padding(paddingValues = it)
        ) {
            when (screenState.state) {
                is SeeAllScreenState.State.Error -> ErrorContent(onRetry = { screenState.getScreenData(swipeRefresh = false) })
                is SeeAllScreenState.State.Loading -> LoadingContent()
                else -> ScreenContent(screenState = screenState)
            }

            LaunchedEffect(
                key1 = screenState.state,
                block = {
                    if (screenState.state is SeeAllScreenState.State.ShowSnackBar) {
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
private fun ScreenContent(screenState: SeeAllScreenState) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = screenState.state is SeeAllScreenState.State.SwipeRefresh)
    val gridState: LazyGridState = rememberLazyGridState()

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { screenState.getScreenData(swipeRefresh = true) }
    ) {
        LazyVerticalGrid(
            state = gridState,
            columns = GridCells.Fixed(count = 2),
            verticalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding),
            horizontalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding),
            contentPadding = PaddingValues(
                start = AppTheme.dimens.screenPadding,
                end = AppTheme.dimens.screenPadding,
                bottom = AppTheme.dimens.screenPadding + imeBottomInsetDp
            ),
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = screenState.items,
                key = { it.id }
            ) { item ->
                when (item) {
                    is SeeAllListItem.Item -> WatchableWithRating(
                        item = item.watchableItem,
                        onClick = { }
                    )
                    is SeeAllListItem.LoadMore -> {
                        //TODO: Implement it (loading circular)
                        screenState.getScreenData(swipeRefresh = false)
                    }
                }
            }
        }
    }
}