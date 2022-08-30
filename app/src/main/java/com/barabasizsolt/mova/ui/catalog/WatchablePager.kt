package com.barabasizsolt.mova.ui.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.barabasizsolt.mova.R
import com.barabasizsolt.mova.ui.theme.AppTheme
import com.barabasizsolt.mova.util.getImageKey
import com.barabasizsolt.mova.util.movieGenres
import com.barabasizsolt.mova.util.withShadow
import com.barabasizsolt.movie.model.Movie
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay


@OptIn(ExperimentalPagerApi::class)
@Composable
fun WatchablePager(
    modifier: Modifier = Modifier,
    pagerContent: List<Movie>
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
                onClick = {}
            )
        }
        //HorizontalPagerIndicator(pagerState = pagerState)
    }
}

@Composable
private fun PagerItem(
    modifier: Modifier = Modifier,
    item: Movie,
    onClick: () -> Unit
) {
//    Column(verticalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding)) {
//
//    }
    Box {
        MovaImage(
            imageUrl = item.backdropPath.getImageKey(),
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(ratio = 1.2f)
                .clickable { onClick() }
        )
        PagerItemInfo(
            item = item,
            modifier = Modifier
                .align(alignment = Alignment.BottomStart)
                .padding(
                    start = AppTheme.dimens.screenPadding,
                    bottom = AppTheme.dimens.screenPadding
                ),
            onPlayButtonClicked = {},
            onAddToFavouriteButtonClicked = {}
        )
    }
}

@Composable
private fun PagerItemInfo(
    modifier: Modifier = Modifier,
    item: Movie,
    onPlayButtonClicked: () -> Unit,
    onAddToFavouriteButtonClicked: () -> Unit
) {
    val genresText = item.genreIds.joinToString(separator = " • ") { genre -> movieGenres[genre].orEmpty() }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.smallPadding)
    ) {
        Text(
            text = item.title,
            style = AppTheme.typography.body1.withShadow(),
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = genresText,
            style = AppTheme.typography.caption.withShadow(),
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            color = Color.White
        )
//        PagerItemButtons(
//            onPlayButtonClicked = onPlayButtonClicked,
//            onAddToFavouriteButtonClicked = onAddToFavouriteButtonClicked
//        )
    }
}

@Composable
private fun PagerItemButtons(
    modifier: Modifier = Modifier,
    onPlayButtonClicked: () -> Unit,
    onAddToFavouriteButtonClicked: () -> Unit
) = Row(
    modifier = modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.screenPadding)
) {
    MovaFilledButton(
        text = "Play",
        icon = painterResource(id = R.drawable.ic_play),
        onClick = onPlayButtonClicked
    )
    MovaOutlinedButton(
        text = "Favourites",
        icon = painterResource(id = R.drawable.ic_favorite),
        contentColor = AppTheme.colors.secondary,
        onClick = onAddToFavouriteButtonClicked
    )
}