package com.barabasizsolt.catalog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.barabasizsolt.theme.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import com.barabasizsolt.mova.ui.catalog.R

@Composable
fun ScrollUpWrapper(
    modifier: Modifier = Modifier,
    gridState: LazyGridState,
    content: @Composable () -> Unit,
    scrollUpTopPadding: Dp = AppTheme.dimens.screenPadding,
    shouldShow: Boolean = true
) {
    val scope: CoroutineScope = rememberCoroutineScope()
    val isVisible by remember(key1 = gridState) {
        derivedStateOf { gridState.firstVisibleItemIndex > 10 }
    }

    Box(modifier = modifier) {
        content()

        AnimatedVisibility(
            visible = isVisible && shouldShow,
            modifier = Modifier
                .align(alignment = Alignment.TopCenter)
                .statusBarsPadding()
                .padding(top = scrollUpTopPadding)
        ) {
            ScrollToTopItem(
                text = stringResource(id = R.string.scroll_up),
                onClick = { scope.launch { gridState.scrollToItem(index = 0, scrollOffset = 0) } },
            )
        }
    }
}

@Composable
private fun ScrollToTopItem(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) = MovaFilledButton(
    text = text,
    icon = Icons.Filled.ArrowUpward,
    onClick = onClick,
    iconSize = 18.dp,
    modifier = modifier
)