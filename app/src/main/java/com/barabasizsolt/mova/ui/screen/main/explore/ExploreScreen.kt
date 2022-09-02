package com.barabasizsolt.mova.ui.screen.main.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.barabasizsolt.mova.R
import com.barabasizsolt.mova.ui.catalog.ErrorContent
import com.barabasizsolt.mova.ui.catalog.LoadingContent
import com.barabasizsolt.mova.ui.catalog.MovaSearchField
import com.barabasizsolt.mova.ui.catalog.WatchableWithRating
import com.barabasizsolt.mova.ui.theme.AppTheme
import com.barabasizsolt.mova.util.statusBarInsetDp

object ExploreScreen  : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(id = R.string.explore_tab)
            val icon = painterResource(id = R.drawable.ic_explore)
            return remember { TabOptions(index = 1u, title = title, icon = icon) }
        }

    @Composable
    override fun Content() {
        val screenModel = getScreenModel<ExploreScreenModel>()
        val state by screenModel.state.collectAsState()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppTheme.colors.primary)
        ) {
            when (state) {
                is ExploreScreenModel.State.Error -> ErrorContent(onRetry = { screenModel.getScreenData(swipeRefresh = false) })
                is ExploreScreenModel.State.Loading -> LoadingContent()
                else -> ExploreContent(screenModel = screenModel, state = state)
            }
        }
    }

    @Composable
    private fun ExploreContent(
        screenModel: ExploreScreenModel,
        state: ExploreScreenModel.State
    ) {
        var query by rememberSaveable { mutableStateOf(value = "") }

        Column(verticalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.screenPadding)) {
            MovaSearchField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier
                    .padding(
                        top = statusBarInsetDp + AppTheme.dimens.screenPadding,
                        start = AppTheme.dimens.screenPadding,
                        end = AppTheme.dimens.screenPadding
                    )
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(count = 2),
                verticalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding),
                horizontalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding),
                contentPadding = PaddingValues(
                    start = AppTheme.dimens.screenPadding,
                    end = AppTheme.dimens.screenPadding,
                    bottom = AppTheme.dimens.screenPadding
                ),
                modifier = Modifier.fillMaxSize()
            ) {
                items(items = screenModel.exploreContent.orEmpty()) { item ->
                    WatchableWithRating(
                        imageKey = item.posterPath,
                        rating = item.secondaryInfo,
                        onClick = { }
                    )
                }
            }
        }
    }
}