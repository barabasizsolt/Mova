package com.barabasizsolt.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.barabasizsolt.base.BaseScreen
import com.barabasizsolt.catalog.R
import com.barabasizsolt.theme.AppTheme

/*TODO: remember the scroll position*/
@Composable
fun DetailScreen(screenState: DetailScreenState) {

    val detailScreenAdapter = rememberDetailScreenAdapter(
        onPlayButtonClicked = { /* TODO: Implement it */ },
        onAddToFavouriteButtonClicked = { /* TODO: Implement it */ },
        onTabIndexChange = screenState::onTabIndexChange,
        onMovieClicked = screenState::onMovieClicked
    )

    LaunchedEffect(
        key1 = screenState.tabIndex,
        block = { detailScreenAdapter.submitList(screenState.screenDetailList) }
    )

    BaseScreen(
        screenState = screenState
    ) { _, _ ->
        AndroidView(
            factory = { context ->
                val recyclerView = RecyclerView(context).apply {
                    layoutManager = GridLayoutManager(context, 2).apply {
                        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                            override fun getSpanSize(position: Int): Int = when (detailScreenAdapter.getItemViewType(position)) {
                                ItemViewType.SIMILAR_MOVIE.ordinal -> 1
                                else -> 2
                            }
                        }
                    }
                    adapter = detailScreenAdapter
                }
                detailScreenAdapter.submitList(screenState.screenDetailList)
                recyclerView
            },
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppTheme.colors.primary)
                .navigationBarsPadding()
        )
    }
}

@Composable
fun EmptyTabItem(modifier: Modifier = Modifier) = Card(
    modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = AppTheme.dimens.screenPadding),
    backgroundColor = AppTheme.colors.secondary,
    elevation = if (isSystemInDarkTheme()) 16.dp else 0.dp,
    shape = AppTheme.shapes.medium
) {
    Text(
        text = stringResource(id = R.string.no_result_found),
        textAlign = TextAlign.Center,
        style = AppTheme.typography.body2,
        color = AppTheme.colors.primary,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxSize()
            .padding(all = AppTheme.dimens.screenPadding)
    )
}