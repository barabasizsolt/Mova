package com.barabasizsolt.mova.ui.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember

object AppTheme {
    val dimens: AppDimens
        @Composable
        @ReadOnlyComposable
        get() = LocalDimens.current

    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val shapes: AppShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current
}

@Composable
fun MovaTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    colors: AppColors = if (isDarkTheme) darkColors() else lightColors(),
    typography: AppTypography = AppTheme.typography,
    dimensions: AppDimens = AppTheme.dimens,
    shapes: AppShapes = AppTheme.shapes,
    content: @Composable () -> Unit
) {
    val rememberedColors = remember { colors.copy() }.apply { updateColorsFrom(colors) }
    val rippleIndication = rememberRipple()
    CompositionLocalProvider(
        LocalColors provides rememberedColors,
        LocalDimens provides dimensions,
        LocalTypography provides typography,
        LocalShapes provides shapes,
        LocalIndication provides rippleIndication,
        content = content
    )
}