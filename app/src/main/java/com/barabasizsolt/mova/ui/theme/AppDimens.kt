package com.barabasizsolt.mova.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal val LocalDimens = staticCompositionLocalOf { AppDimens() }

data class AppDimens(
    val screenPadding: Dp = 20.dp,
    val contentPadding: Dp = 8.dp,
    val smallPadding: Dp = 4.dp,
    val buttonSize: Dp = 56.dp
)