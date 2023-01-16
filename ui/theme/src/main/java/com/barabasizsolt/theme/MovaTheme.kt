package com.barabasizsolt.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import com.barabasizsolt.theme.attributes.AppColor
import com.barabasizsolt.theme.attributes.AppDimens
import com.barabasizsolt.theme.attributes.AppShape
import com.barabasizsolt.theme.attributes.AppTypography
import com.barabasizsolt.theme.attributes.LocalColors
import com.barabasizsolt.theme.attributes.LocalDimens
import com.barabasizsolt.theme.attributes.LocalShapes
import com.barabasizsolt.theme.attributes.LocalTypography
import com.barabasizsolt.theme.attributes.darkColors
import com.barabasizsolt.theme.attributes.darkTypography
import com.barabasizsolt.theme.attributes.lightColors
import com.barabasizsolt.theme.attributes.lightTypography

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
        content = content
    )
}