package com.barabasizsolt.catalog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.barabasizsolt.theme.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue

@Composable
fun ScrollUpWrapper(
    modifier: Modifier = Modifier,
    gridState: LazyGridState,
    content: @Composable () -> Unit
) {
    val scrollToUpIsVisible = rememberSaveable { mutableStateOf(value = false) }
    val scope: CoroutineScope = rememberCoroutineScope()

    val keyToShow by remember { derivedStateOf { gridState.firstVisibleItemIndex > 20 } }
    val keyToHide by remember { derivedStateOf { gridState.firstVisibleItemIndex < 1 } }

    LaunchedEffect(key1 = keyToShow, block = { scrollToUpIsVisible.value = true })
    LaunchedEffect(key1 = keyToHide, block = { scrollToUpIsVisible.value = false })

    Box(modifier = modifier) {
        content()

        AnimatedVisibility(
            visible = scrollToUpIsVisible.value,
            modifier = Modifier
                .align(alignment = Alignment.TopCenter)
                .padding(top = AppTheme.dimens.screenPadding * 2)
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