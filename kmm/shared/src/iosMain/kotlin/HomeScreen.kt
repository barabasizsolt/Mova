package com.barabasizsolt.mova.shared

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
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.barabasizsolt.mova.domain.model.ContentItem
import platform.CoreGraphics.CGRectGetHeight
import platform.UIKit.UIScreen
import ui.catalog.ErrorContent
import ui.catalog.ErrorItem
import ui.catalog.LoadingContent
import ui.catalog.MovaHeader
import ui.catalog.MovaSnackBar
import ui.catalog.WatchableWithRating
import ui.screen.base.UserAction
import ui.theme.AppTheme

@Composable
fun HomeScreen(
    snackBarModifier: Modifier = Modifier,
    screenState: HomeScreenState,
    onSnackBarDismissed: (() -> Unit)? = null,
) {
    val snackBarHostState: SnackbarHostState = remember { SnackbarHostState() }
    val gridState: LazyGridState = rememberLazyGridState()

    Box(modifier = Modifier.background(color = AppTheme.colors.primary).fillMaxSize()) {
        when (screenState.state) {
            is HomeScreenState.State.Error -> ErrorContent(onRetry = { screenState.getScreenData(userAction = UserAction.Error) })
            is HomeScreenState.State.Loading -> LoadingContent()
            else -> ScreenContent(
                items = screenState.watchableItems,
                onLoadMoreItem = { screenState.getScreenData(userAction = UserAction.Normal) },
                onRetryClick = { screenState.getScreenData(userAction = UserAction.TryAgain) },
                gridState = gridState,
                isTryAgainLoading = screenState.state is HomeScreenState.State.TryAgainLoading
            )
        }

        MovaSnackBar(
            snackBarHostState = snackBarHostState,
            onDismiss = {
                snackBarHostState.currentSnackbarData?.dismiss()
                if (onSnackBarDismissed != null) {
                    onSnackBarDismissed()
                }
            },
            modifier = snackBarModifier
        )

        LaunchedEffect(
            key1 = screenState.state,
            block = {
                if (screenState.state is HomeScreenState.State.ShowSnackBar) {
                    snackBarHostState.showSnackbar(
                        message = "Oops, something went wrong.",
                        actionLabel = "Try Again"
                    )
                }
            }
        )

        DisposableEffect(
            key1 = Unit,
            effect = { onDispose { screenState.onClear() } }
        )
    }
}

@Composable
private fun ScreenContent(
    items: List<ContentItem>,
    onLoadMoreItem: () -> Unit,
    onRetryClick: () -> Unit,
    gridState: LazyGridState,
    isTryAgainLoading: Boolean
) = LazyVerticalGrid(
    state = gridState,
    columns = GridCells.Fixed(count = 6),
    verticalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding),
    horizontalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding),
    contentPadding = PaddingValues(
        start = AppTheme.dimens.screenPadding,
        end = AppTheme.dimens.screenPadding,
        bottom = AppTheme.dimens.screenPadding,
        top = AppTheme.dimens.screenPadding
    )
) {
    content(
        items = items,
        onLoadMoreItem = onLoadMoreItem,
        onRetryClick = onRetryClick,
        isTryAgainLoading = isTryAgainLoading
    )
}

private fun LazyGridScope.content(
    items: List<ContentItem>,
    onLoadMoreItem: () -> Unit,
    onRetryClick: () -> Unit,
    isTryAgainLoading: Boolean
) = itemsIndexed(
    items = items,
    key = { index, item -> item.id + index },
    span = { _, item ->
        GridItemSpan(
            currentLineSpan = when (item) {
                is ContentItem.ItemHeader, is ContentItem.ItemTail, is ContentItem.ItemError -> 6
                is ContentItem.Person -> 2
                else -> 3
            }
        )
    }
) { _, item ->
    when (item) {
        is ContentItem.ItemHeader -> MovaHeader(
            text = "Bounds: ${UIScreen.mainScreen.bounds.let { bounds ->
                CGRectGetHeight(bounds)
            }}",
            onClick = {},
            modifier = Modifier.padding(bottom = AppTheme.dimens.contentPadding)
        )
        is ContentItem.Watchable ->
            WatchableWithRating(item = item, onClick = {})
        is ContentItem.ItemTail -> if (item.loadMore) {
            LoadingContent(modifier = Modifier
                .height(height = 80.dp)
                .fillMaxWidth())
            LaunchedEffect(
                key1 = Unit,
                block = { onLoadMoreItem() }
            )
        }
        is ContentItem.ItemError -> ErrorItem(
            onRetryClick = onRetryClick,
            isLoading = isTryAgainLoading
        )
        else -> Unit
    }
}