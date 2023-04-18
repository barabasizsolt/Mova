package com.barabasizsolt.mova.ui.catalog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.barabasizsolt.mova.ui.R
import com.barabasizsolt.mova.ui.theme.AppTheme
import com.barabasizsolt.mova.ui.util.ImageType
import com.barabasizsolt.mova.ui.util.getImageKey
import com.barabasizsolt.mova.ui.util.withShadow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import movie.model.Movie

@OptIn(ExperimentalPagerApi::class)
@Composable
fun WatchablePager(
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
            if (pagerState.pageCount > 0) {
                pagerState.animateScrollToPage(page = (pagerState.currentPage + 1) % (pagerState.pageCount))
            }
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        HorizontalPager(
            modifier = modifier.fillMaxWidth(),
            count = pagerContent.size,
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
            activeColor = AppTheme.colors.secondary,
            inactiveColor = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray
        )
    }
}

@Composable
private fun PagerItem(
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
private fun PagerItemInfo(
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
private fun PagerGenreItem(
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
private fun PagerItemButtons(
    modifier: Modifier = Modifier,
    onPlayButtonClicked: () -> Unit,
    onAddToFavouriteButtonClicked: () -> Unit
) = Row(
    modifier = modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding * 2)
) {
    MovaFilledButton(
        text = stringResource(id = R.string.trailer),
        icon = Icons.Filled.PlayCircle,
        onClick = onPlayButtonClicked
    )
    MovaOutlinedButton(
        text = stringResource(id = R.string.favourites),
        icon = Icons.Filled.Favorite,
        contentColor = AppTheme.colors.secondary,
        onClick = onAddToFavouriteButtonClicked
    )
}