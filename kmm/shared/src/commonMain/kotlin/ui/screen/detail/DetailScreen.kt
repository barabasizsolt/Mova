package ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.barabasizsolt.mova.domain.model.ContentItem
import com.barabasizsolt.mova.domain.model.toContentItem
import org.koin.core.component.KoinComponent
import ui.catalog.WatchableWithRating
import ui.screen.detail.catalog.ContentHeader
import ui.screen.detail.catalog.ContentTabs
import ui.screen.detail.catalog.Review
import ui.theme.AppTheme

internal expect class DetailScreen(id: Int) : Screen, KoinComponent

@Composable
internal fun ScreenContent(
    modifier: Modifier = Modifier,
    items: List<DetailScreenListItem>,
    gridState: LazyGridState,
    tabs: List<String>,
    onPlayButtonClicked: () -> Unit,
    onAddToFavouriteButtonClicked: () -> Unit,
    onTabIndexChange: (Int) -> Unit,
    onMovieClicked: (Int) -> Unit,
) = LazyVerticalGrid(
    modifier = modifier.fillMaxSize().background(color = AppTheme.colors.primary),
    columns = GridCells.Fixed(count = 2),
    state = gridState,
    verticalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding * 2),
    horizontalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding),
    contentPadding = PaddingValues(bottom = ui.getPlatform(). navigationBarInsetDp),
    content = {
        items(
            items = items,
            span = { item ->
                when (item) {
                    is DetailScreenListItem.SimilarMovieItem -> GridItemSpan(currentLineSpan = 1)
                    else -> GridItemSpan(currentLineSpan = 2)
                }
            }
        ) { item ->
            when (item) {
                is DetailScreenListItem.HeaderItem -> ContentHeader(
                    item = item,
                    onPlayButtonClicked = onPlayButtonClicked,
                    onAddToFavouriteButtonClicked = onAddToFavouriteButtonClicked
                )
                is DetailScreenListItem.TabsItem -> ContentTabs(
                    tabs = tabs,
                    onTabIndexChange = onTabIndexChange,
                    modifier = Modifier.offset(y = -AppTheme.dimens.contentPadding)
                )
                is DetailScreenListItem.SimilarMovieItem -> WatchableWithRating(
                    item = item.movie.toContentItem() as ContentItem.Watchable,
                    onClick = onMovieClicked,
                    modifier = Modifier.padding(
                        start = if (item.movieIndex % 2 == 0) AppTheme.dimens.screenPadding else AppTheme.dimens.smallPadding,
                        end = if (item.movieIndex % 2 == 0) AppTheme.dimens.smallPadding else AppTheme.dimens.screenPadding,
                        top = AppTheme.dimens.contentPadding,
                        bottom = if (item.isLast) AppTheme.dimens.screenPadding else 0.dp
                    )
                )
                is DetailScreenListItem.ReviewItem -> Review(
                    review = item.review,
                    modifier = Modifier.padding(
                        top = AppTheme.dimens.contentPadding,
                        bottom = if (item.isLast) AppTheme.dimens.screenPadding else AppTheme.dimens.smallPadding
                    )
                )
                is DetailScreenListItem.EmptyItem -> EmptyTabItem()
                is DetailScreenListItem.VideoItem -> Unit
            }
        }
    }
)

@Composable
internal fun EmptyTabItem(modifier: Modifier = Modifier) = Card(
    modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = AppTheme.dimens.screenPadding),
    backgroundColor = AppTheme.colors.secondary,
    elevation = 16.dp,
    shape = AppTheme.shapes.medium
) {
    Text(
        text = AppTheme.strings.noResultFound,
        textAlign = TextAlign.Center,
        style = AppTheme.typography.body2,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxSize()
            .padding(all = AppTheme.dimens.screenPadding)
    )
}