package com.barabasizsolt.mova.ui.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import com.barabasizsolt.mova.ui.Platform
import com.barabasizsolt.mova.ui.getImageLoader
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.LocalImageLoader
import com.seiko.imageloader.component.setupBase64Components
import com.seiko.imageloader.component.setupCommonComponents
import com.seiko.imageloader.component.setupKtorComponents
import com.seiko.imageloader.util.DebugLogger
import com.seiko.imageloader.util.LogPriority

object AppTheme {
    val dimens: AppDimens
        @Composable
        @ReadOnlyComposable
        get() = LocalDimens.current

    val colors: AppColor
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val shapes: AppShape
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current
}

@Composable
fun MovaTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    colors: AppColor = if (isDarkTheme) darkColors else lightColors,
    typography: AppTypography = if (isDarkTheme) darkTypography else lightTypography,
    dimensions: AppDimens = AppTheme.dimens,
    shapes: AppShape = AppTheme.shapes,
    content: @Composable () -> Unit
) {
    val rippleIndication = rememberRipple()
    CompositionLocalProvider(
        LocalColors provides colors,
        LocalDimens provides dimensions,
        LocalTypography provides typography,
        LocalShapes provides shapes,
        LocalIndication provides rippleIndication,
        LocalImageLoader provides getImageLoader(),
        content = content
    )
}