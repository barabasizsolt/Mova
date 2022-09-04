package com.barabasizsolt.mova.ui.screen.main.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.barabasizsolt.mova.R
import com.barabasizsolt.mova.ui.catalog.ErrorContent
import com.barabasizsolt.mova.ui.catalog.FilterIcon
import com.barabasizsolt.mova.ui.catalog.LoadingContent
import com.barabasizsolt.mova.ui.catalog.MovaSearchField
import com.barabasizsolt.mova.ui.catalog.SearchableItem
import com.barabasizsolt.mova.ui.catalog.WatchableWithRating
import com.barabasizsolt.mova.ui.theme.AppTheme
import com.barabasizsolt.mova.util.imeBottomInsetDp
import com.barabasizsolt.mova.util.statusBarInsetDp

object ExploreScreen  : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(id = R.string.explore_tab)
            val icon = painterResource(id = R.drawable.ic_explore)
            return remember { TabOptions(index = 1u, title = title, icon = icon) }
        }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        val screenModel = getScreenModel<ExploreScreenModel>()
        val state by screenModel.state.collectAsState()

        BottomSheetNavigator(
            sheetShape = AppTheme.shapes.medium.copy(
                bottomStart = CornerSize(size = 0.dp),
                bottomEnd = CornerSize(size = 0.dp)
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = AppTheme.colors.primary)
            ) {
                when (state) {
                    is ExploreScreenModel.State.Error -> ErrorContent(onRetry = { screenModel.getScreenData(isSearch = false) })
                    is ExploreScreenModel.State.Loading -> LoadingContent()
                    else -> ExploreContent(screenModel = screenModel, state = state)
                }
            }
        }
    }

    @Composable
    private fun ExploreContent(
        screenModel: ExploreScreenModel,
        state: ExploreScreenModel.State
    ) {
        val bottomSheetNavigator = LocalBottomSheetNavigator.current

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
                    value = screenModel.query,
                    onValueChange = screenModel::onQueryChange,
                    modifier = Modifier.weight(weight = 1f)
                )
                FilterIcon(onClick = { bottomSheetNavigator.show(screen = FilterScreen) })
            }
            if (state is ExploreScreenModel.State.Search) {
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
                    if (screenModel.query.isNotEmpty()) {
                        items(
                            items = screenModel.exploreContent.orEmpty(),
                            span = { GridItemSpan(currentLineSpan = 2) }
                        ) { item ->
                            SearchableItem(
                                item = item,
                                onClick = { }
                            )
                        }
                    } else {
                        items(items = screenModel.exploreContent.orEmpty()) { item ->
                            WatchableWithRating(
                                item = item,
                                onClick = { }
                            )
                        }
                    }
                }
            }
        }
    }
}