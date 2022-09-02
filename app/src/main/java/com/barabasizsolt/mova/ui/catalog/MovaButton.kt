package com.barabasizsolt.mova.ui.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.barabasizsolt.mova.R
import com.barabasizsolt.mova.ui.theme.AppTheme

@Composable
fun MovaFilledButton(
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    text: String,
    backgroundColor: Color = AppTheme.colors.secondary,
    contentColor: Color = Color.White,
    onClick: () -> Unit
) = Box(
    modifier = modifier
        .clip(shape = CircleShape)
        .background(color = backgroundColor)
        .clickable { onClick() }
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(
            horizontal = AppTheme.dimens.contentPadding * 2,
            vertical = AppTheme.dimens.contentPadding
        )
    ) {
        if (icon != null) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = contentColor
            )
        }
        Text(
            text = text,
            style = AppTheme.typography.subtitle1,
            color = contentColor
        )
    }
}

@Composable
fun MovaOutlinedButton(
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    text: String,
    contentColor: Color = Color.White,
    onClick: () -> Unit
) = Box(
    modifier = modifier
        .clip(shape = CircleShape)
        .background(color = Color.Transparent)
        .border(
            color = contentColor,
            width = 2.dp,
            shape = CircleShape
        )
        .clickable { onClick() }
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(
            horizontal = AppTheme.dimens.contentPadding * 2,
            vertical = AppTheme.dimens.contentPadding
        )
    ) {
        if (icon != null) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = contentColor
            )
        }
        Text(
            text = text,
            style = AppTheme.typography.subtitle1,
            color = contentColor
        )
    }
}

@Composable
fun MovaButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) = Box(
    modifier = modifier
        .height(height = AppTheme.dimens.buttonSize)
        .fillMaxWidth()
        .clip(shape = CircleShape)
        .background(color = AppTheme.colors.secondary)
        .clickable { onClick() }
) {
    Text(
        text = text,
        color = Color.White,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier.align(alignment = Alignment.Center),
        style = AppTheme.typography.body1,
        fontWeight = FontWeight.Bold
    )
}