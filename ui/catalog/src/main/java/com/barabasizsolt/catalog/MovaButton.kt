package com.barabasizsolt.catalog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.barabasizsolt.theme.attributes.AppTheme

@Composable
fun MovaFilledButton(
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    text: String,
    backgroundColor: Color = AppTheme.colors.secondary,
    contentColor: Color = Color.White,
    textStyle: TextStyle = AppTheme.typography.subtitle1,
    onClick: () -> Unit
) = Button(
    modifier = modifier,
    onClick = onClick,
    shape = CircleShape,
    colors = ButtonDefaults.buttonColors(
        backgroundColor = backgroundColor
    ),
    content = {
        ButtonContent(
            icon = icon,
            text = text,
            contentColor = contentColor,
            textStyle = textStyle
        )
    }
)

@Composable
fun MovaOutlinedButton(
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    text: String,
    contentColor: Color = Color.White,
    textStyle: TextStyle = AppTheme.typography.subtitle1,
    onClick: () -> Unit
) = Button(
    modifier = modifier,
    onClick = onClick,
    shape = CircleShape,
    border = BorderStroke(
        color = contentColor,
        width = 2.dp
    ),
    colors = ButtonDefaults.buttonColors(
        backgroundColor = AppTheme.colors.background
    ),
    content = {
        ButtonContent(
            icon = icon,
            text = text,
            contentColor = contentColor,
            textStyle = textStyle
        )
    }
)

@Composable
fun MovaButton(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color = AppTheme.colors.secondary,
    contentColor: Color = Color.White,
    onClick: () -> Unit
) = Button(
    modifier = modifier
        .height(height = AppTheme.dimens.buttonSize)
        .fillMaxWidth(),
    onClick = onClick,
    colors = ButtonDefaults.buttonColors(
        backgroundColor = backgroundColor
    ),
    shape = CircleShape
) {
    Text(
        text = text,
        color = contentColor,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = AppTheme.typography.body1,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun ButtonContent(
    icon: Painter?,
    text: String,
    contentColor: Color,
    textStyle: TextStyle,
) {
    if (icon != null) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = contentColor
        )
    }
    Spacer(modifier = Modifier.width(width = AppTheme.dimens.contentPadding))
    Text(
        text = text,
        style = textStyle,
        color = contentColor
    )
}