package ui.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

object AppTheme {
    internal val dimens: AppDimens
        @Composable
        @ReadOnlyComposable
        get() = LocalDimens.current

    internal val colors: AppColor
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    internal val typography: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    internal val shapes: AppShape
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