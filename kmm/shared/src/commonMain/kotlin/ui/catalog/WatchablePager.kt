package ui.catalog

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import kotlinx.coroutines.delay
import movie.model.Movie
import ui.theme.AppTheme
import ui.util.ImageType
import ui.util.getImageKey
import ui.util.withShadow

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun WatchablePager(
    modifier: Modifier = Modifier,
    pagerContent: List<Movie>,
    genres: Map<Long, String>,
    onClick: (Int) -> Unit,
    onPlayButtonClicked: () -> Unit,
    onAddToFavouriteButtonClicked: () -> Unit
) {
    val pagerState = rememberPagerState()
    LaunchedEffect(Unit) {
        while (true) {
            delay(timeMillis = 5000)
            if (pagerContent.isNotEmpty()) {
                pagerState.animateScrollToPage(page = (pagerState.currentPage + 1) % (pagerContent.size))
            }
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        HorizontalPager(
            modifier = modifier.fillMaxWidth(),
            pageCount = pagerContent.size,
            state = pagerState
        ) { page ->
            PagerItem(
                item = pagerContent[page],
                onClick = { onClick(pagerContent[page].id.toInt()) },
                onPlayButtonClicked = onPlayButtonClicked,
                onAddToFavouriteButtonClicked = onAddToFavouriteButtonClicked,
                genres = genres
            )
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            indicatorCount = pagerContent.size,
            itemCount = pagerContent.size,
            modifier = Modifier.padding(horizontal = AppTheme.dimens.screenPadding)
        )
    }
}

@Composable
internal fun PagerItem(
    modifier: Modifier = Modifier,
    item: Movie,
    genres: Map<Long, String>,
    onClick: () -> Unit,
    onPlayButtonClicked: () -> Unit,
    onAddToFavouriteButtonClicked: () -> Unit
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .clickable { onClick() }) {
        MovaImage(
            imageUrl = item.posterPath?.getImageKey(imageType = ImageType.ORIGINAL).orEmpty(),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio = 0.7f)
        )
        GradientOverlay(
            maxHeightFraction = 0.5f,
            colors = listOf(
                Color.Transparent,
                Color.Transparent,
                AppTheme.colors.primary.copy(alpha = 0.8f),
                AppTheme.colors.primary
            )
        )
        PagerItemInfo(
            item = item,
            genres = genres,
            modifier = Modifier.align(alignment = Alignment.BottomStart),
            onPlayButtonClicked = onPlayButtonClicked,
            onAddToFavouriteButtonClicked = onAddToFavouriteButtonClicked
        )
    }
}

@Composable
internal fun PagerItemInfo(
    modifier: Modifier = Modifier,
    item: Movie,
    genres: Map<Long, String>,
    onPlayButtonClicked: () -> Unit,
    onAddToFavouriteButtonClicked: () -> Unit
) = Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding)
) {
    Text(
        text = item.title,
        style = AppTheme.typography.h6.withShadow(),
        fontWeight = FontWeight.Bold,
        color = Color.White,
        modifier = Modifier.padding(horizontal = AppTheme.dimens.screenPadding)
    )
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(horizontal = AppTheme.dimens.screenPadding)
    ) {
        itemsIndexed(items = item.genreIds.map { genre -> genres[genre.toLong()].orEmpty() }) { index, genre ->
            PagerGenreItem(
                text = genre,
                shouldShowSeparator = index != item.genreIds.lastIndex
            )
        }
    }
    PagerItemButtons(
        onPlayButtonClicked = onPlayButtonClicked,
        onAddToFavouriteButtonClicked = onAddToFavouriteButtonClicked,
        modifier = Modifier.padding(
            start = AppTheme.dimens.screenPadding,
            bottom = AppTheme.dimens.screenPadding
        )
    )
}

@Composable
internal fun PagerGenreItem(
    modifier: Modifier = Modifier,
    text: String,
    shouldShowSeparator: Boolean
) = Text(
    text = if (shouldShowSeparator) "$text • " else text,
    style = AppTheme.typography.body2.withShadow(),
    fontWeight = FontWeight.Bold,
    overflow = TextOverflow.Ellipsis,
    color = Color.White,
    modifier = modifier
)

@Composable
internal fun PagerItemButtons(
    modifier: Modifier = Modifier,
    onPlayButtonClicked: () -> Unit,
    onAddToFavouriteButtonClicked: () -> Unit
) = Row(
    modifier = modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding * 2)
) {
    MovaFilledButton(
        text = "Trailer",
        icon = Icons.Filled.PlayCircle,
        onClick = onPlayButtonClicked
    )
    MovaOutlinedButton(
        text = "Favourites",
        icon = Icons.Filled.Favorite,
        contentColor = AppTheme.colors.secondary,
        onClick = onAddToFavouriteButtonClicked
    )
}