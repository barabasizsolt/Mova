package com.barabasizsolt.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.barabasizsolt.catalog.ErrorContent
import com.barabasizsolt.catalog.FilterIcon
import com.barabasizsolt.catalog.LoadingContent
import com.barabasizsolt.catalog.MovaSearchField
import com.barabasizsolt.catalog.SearchableItem
import com.barabasizsolt.catalog.WatchableWithRating
import com.barabasizsolt.domain.model.WatchableItem
import com.barabasizsolt.theme.attributes.AppTheme
import com.barabasizsolt.util.imeBottomInsetDp
import com.barabasizsolt.util.statusBarInsetDp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExploreScreen(screenState: ExploreScreenState) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()

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
            when (screenState.state) {
                is ExploreScreenState.State.Error -> ErrorContent(onRetry = { screenState.getScreenData(isSearch = false) })
                is ExploreScreenState.State.Loading -> LoadingContent()
                else -> ScreenContent(
                    query = screenState.query,
                    onQueryChange = screenState::onQueryChange,
                    items = screenState.exploreContent.orEmpty(),
                    isLoading = screenState.state is ExploreScreenState.State.Search,
                    bottomSheetScaffoldState = bottomSheetScaffoldState
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ScreenContent(
    query: String,
    onQueryChange: (String) -> Unit,
    items: List<WatchableItem>,
    isLoading: Boolean,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
) {
    val scope = rememberCoroutineScope()

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
            LoadingContent()
        } else {
            LazyVerticalGrid(
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
                if (query.isNotEmpty()) {
                    items(
                        items = items,
                        span = { GridItemSpan(currentLineSpan = 2) }
                    ) { item ->
                        SearchableItem(
                            item = item,
                            onClick = { /*TODO: Implement it*/ }
                        )
                    }
                } else {
                    items(items = items) { item ->
                        WatchableWithRating(
                            item = item,
                            onClick = { /*TODO: Implement it*/ }
                        )
                    }
                }
            }
        }
    }
}