package com.barabasizsolt.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.barabasizsolt.base.BaseScreen
import com.barabasizsolt.base.BaseScreenState
import com.barabasizsolt.catalog.FilterIcon
import com.barabasizsolt.catalog.LoadingContent
import com.barabasizsolt.catalog.MovaSearchField
import com.barabasizsolt.catalog.SearchableItem
import com.barabasizsolt.catalog.WatchableWithRating
import com.barabasizsolt.domain.model.ContentItem
import com.barabasizsolt.theme.AppTheme
import com.barabasizsolt.util.imeBottomInsetDp
import com.barabasizsolt.util.statusBarInsetDp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExploreScreen(screenState: ExploreScreenState) = BaseScreen(
    screenState = screenState,
    content = {
        val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
        val scope = rememberCoroutineScope()
        val gridState: LazyGridState = rememberLazyGridState()

        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetShape = AppTheme.shapes.medium.copy(
                bottomStart = CornerSize(size = 0.dp),
                bottomEnd = CornerSize(size = 0.dp)
            ),
            sheetContent = { FilterScreen() },
            sheetPeekHeight = 0.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = AppTheme.colors.primary)
            ) {
                ScreenContent(
                    query = screenState.query,
                    onQueryChange = screenState::onQueryChange,
                    items = screenState.exploreContent,
                    isLoading = screenState.state is BaseScreenState.State.UserAction,
                    bottomSheetScaffoldState = bottomSheetScaffoldState,
                    onLoadMoreItem = { screenState.getScreenData(isUserAction = false) },
                    scope = scope,
                    gridState = gridState
                )
            }
        }
    }
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ScreenContent(
    query: String,
    onQueryChange: (String) -> Unit,
    items: List<ContentItem>,
    isLoading: Boolean,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    onLoadMoreItem: () -> Unit,
    scope: CoroutineScope,
    gridState: LazyGridState
) {
    Column(verticalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.screenPadding),) {
        Row(
            modifier = Modifier
                .padding(
                    top = statusBarInsetDp + AppTheme.dimens.screenPadding,
                    start = AppTheme.dimens.screenPadding,
                    end = AppTheme.dimens.screenPadding
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding)
        ) {
            MovaSearchField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier.weight(weight = 1f)
            )
            FilterIcon(
                onClick = {
                    scope.launch {
                        if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
                            bottomSheetScaffoldState.bottomSheetState.collapse()
                        } else {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        }
                    }
                }
            )
        }
        if (isLoading) {
            LaunchedEffect(
                key1 = Unit,
                block = { gridState.scrollToItem(index = 0, scrollOffset = 0) }
            )
            LoadingContent()
        } else {
            // TODO [MID] if the search returns empty list show some dialog
            LazyVerticalGrid(
                columns = GridCells.Fixed(count = 2),
                verticalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding),
                horizontalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding),
                contentPadding = PaddingValues(
                    start = AppTheme.dimens.screenPadding,
                    end = AppTheme.dimens.screenPadding,
                    bottom = AppTheme.dimens.screenPadding + imeBottomInsetDp
                ),
                modifier = Modifier.fillMaxSize(),
                state = gridState
            ) {
                if (query.isNotEmpty()) {
                    searchableItemsIndexed(
                        items = items,
                        key = { index, item -> item.id + index },
                        span = { _, item ->
                            GridItemSpan(
                                currentLineSpan = when (item) {
                                    is ContentItem.ItemTail -> 6
                                    else -> 2
                                }
                            )
                        },
                        onLoadMoreItem = onLoadMoreItem
                    ) { _, item ->
                        SearchableItem(
                            item = item as ContentItem.Watchable,
                            onClick = { /*TODO: Implement it*/ }
                        )
                    }
                } else {
                    searchableItemsIndexed(
                        items = items,
                        key = { index, item -> item.id + index },
                        span = { _, item ->
                            GridItemSpan(
                                currentLineSpan = when (item) {
                                    is ContentItem.ItemTail -> 6
                                    else -> 1
                                }
                            )
                        },
                        onLoadMoreItem = onLoadMoreItem
                    ) { _, item ->
                        WatchableWithRating(
                            item = item as ContentItem.Watchable,
                            onClick = { /*TODO: Implement it*/ }
                        )
                    }
                }
            }
        }
    }
}

private inline fun LazyGridScope.searchableItemsIndexed(
    items: List<ContentItem>,
    crossinline onLoadMoreItem: () -> Unit,
    noinline key: ((index: Int, item: ContentItem) -> Any)? = null,
    noinline span: (LazyGridItemSpanScope.(index: Int, item: ContentItem) -> GridItemSpan)? = null,
    crossinline contentType: (index: Int, item: ContentItem) -> Any? = { _, _ -> null },
    crossinline itemContent: @Composable LazyGridItemScope.(index: Int, item: ContentItem) -> Unit
) = itemsIndexed(
    items = items,
    key = key,
    span = span,
    contentType = contentType
) { index, item ->
    when (item) {
        is ContentItem.ItemTail -> if (item.loadMore) {
            LoadingContent(modifier = Modifier.height(height = 80.dp).fillMaxWidth())
            SideEffect { onLoadMoreItem() }
        }
        else -> itemContent(index, item)
    }
}