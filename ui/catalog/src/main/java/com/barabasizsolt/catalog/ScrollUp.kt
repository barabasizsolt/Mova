package com.barabasizsolt.catalog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScrollToTopItem(
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