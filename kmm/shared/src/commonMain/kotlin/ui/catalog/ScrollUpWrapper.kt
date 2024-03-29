package ui.catalog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import ui.theme.AppTheme

@Composable
internal fun ScrollUpWrapper(
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
                .padding(top = scrollUpTopPadding + ui.getPlatform().statusBarInsetDp)
        ) {
            ScrollToTopItem(
                text = AppTheme.strings.scrollUp,
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