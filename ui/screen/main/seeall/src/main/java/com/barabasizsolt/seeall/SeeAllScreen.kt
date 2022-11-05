package com.barabasizsolt.seeall

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.barabasizsolt.catalog.ErrorContent
import com.barabasizsolt.catalog.LoadingContent
import com.barabasizsolt.catalog.WatchableWithRating
import com.barabasizsolt.domain.model.WatchableItem
import com.barabasizsolt.theme.attributes.AppTheme
import com.barabasizsolt.util.navigationBarInsetDp
import com.barabasizsolt.util.statusBarInsetDp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun SeeAllScreen(screenState: SeeAllScreenState) {
    val scaffoldState = rememberScaffoldState()

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
                else -> ScreenContent(
                    isRefreshing = screenState.state is SeeAllScreenState.State.SwipeRefresh,
                    onRefresh = { screenState.getScreenData(swipeRefresh = true) },
                    items = screenState.watchableItems,
                    onLoadMoreItem = { screenState.getScreenData(swipeRefresh = false) }
                )
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
private fun ScreenContent(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    items: List<WatchableItem>,
    onLoadMoreItem: () -> Unit
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)
    val gridState: LazyGridState = rememberLazyGridState()

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = onRefresh
    ) {
        LazyVerticalGrid(
            state = gridState,
            columns = GridCells.Fixed(count = 2),
            verticalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding),
            horizontalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding),
            contentPadding = PaddingValues(
                start = AppTheme.dimens.screenPadding,
                end = AppTheme.dimens.screenPadding,
                bottom = AppTheme.dimens.screenPadding + navigationBarInsetDp,
                top = AppTheme.dimens.screenPadding + statusBarInsetDp
            ),
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(
                items = items,
                key = { _, item -> item.id }
            ) { index, item ->
                WatchableWithRating(item = item, onClick = { /*TODO: Implement it*/ })
                if (index == items.lastIndex) { SideEffect { onLoadMoreItem() } }
            }
            item(span = { GridItemSpan(currentLineSpan = 2) }) {
                LoadingContent(modifier = Modifier
                    .height(height = 80.dp)
                    .fillMaxWidth())
            }
        }
    }
}