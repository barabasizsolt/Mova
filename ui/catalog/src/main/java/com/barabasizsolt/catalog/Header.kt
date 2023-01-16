package com.barabasizsolt.catalog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.barabasizsolt.theme.AppTheme

@Composable
fun MovaHeader(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector = Icons.Filled.ArrowBackIosNew,
    onClick: () -> Unit
) = Box(modifier = modifier.fillMaxWidth()) {
    Icon(
        imageVector = icon,
        contentDescription = null,
        tint = AppTheme.colors.onBackground,
        modifier = Modifier
            .align(alignment = Alignment.CenterStart)
            .size(size = 30.dp)
            .clip(shape = CircleShape)
            .clickable { onClick() }
    )

    Text(
        text = text                          ,
        style = AppTheme.typography.h6,
        fontWeight = FontWeight.Bold,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .align(alignment = Alignment.Center)
            .padding(horizontal = AppTheme.dimens.screenPadding * 2)
    )
}