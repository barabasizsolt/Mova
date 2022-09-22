package com.barabasizsolt.catalog

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.barabasizsolt.theme.attributes.AppTheme

@Composable
fun MovaFilledButton(
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    text: String,
    backgroundColor: Color = AppTheme.colors.secondary,
    contentColor: Color = Color.White,
    horizontalPadding: Dp = AppTheme.dimens.contentPadding * 2,
    verticalPadding: Dp = AppTheme.dimens.contentPadding,
    textStyle: TextStyle = AppTheme.typography.subtitle1,
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
        modifier = Modifier
            .padding(
                horizontal = horizontalPadding,
                vertical = verticalPadding
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
            style = textStyle,
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
    horizontalPadding: Dp = AppTheme.dimens.contentPadding * 2,
    verticalPadding: Dp = AppTheme.dimens.contentPadding,
    textStyle: TextStyle = AppTheme.typography.subtitle1,
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
        modifier = Modifier
            .padding(
                horizontal = horizontalPadding,
                vertical = verticalPadding
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
            style = textStyle,
            color = contentColor
        )
    }
}

@Composable
fun MovaButton(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color = AppTheme.colors.secondary,
    contentColor: Color = Color.White,
    onClick: () -> Unit
) = Box(
    modifier = modifier
        .height(height = AppTheme.dimens.buttonSize)
        .fillMaxWidth()
        .clip(shape = CircleShape)
        .background(color = backgroundColor)
        .clickable { onClick() }
) {
    Text(
        text = text,
        color = contentColor,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.align(alignment = Alignment.Center),
        style = AppTheme.typography.body1,
        fontWeight = FontWeight.Bold
    )
}