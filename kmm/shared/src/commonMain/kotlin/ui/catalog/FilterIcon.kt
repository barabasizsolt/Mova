package ui.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.theme.AppTheme

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun FilterIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) = Box(
    modifier = modifier
        .clip(shape = AppTheme.shapes.medium)
        .size(size = AppTheme.dimens.buttonSize)
        .background(
            color = AppTheme.colors.secondary.copy(alpha = .2f),
            shape = AppTheme.shapes.medium
        )
        .clickable { onClick() }
) {
    Icon(
        painter = painterResource(res = "drawable/ic_filter.xml"),
        contentDescription = null,
        tint = AppTheme.colors.secondary.copy(alpha = .9f),
        modifier = Modifier
            .size(size = 22.dp)
            .align(alignment = Alignment.Center)
    )
}