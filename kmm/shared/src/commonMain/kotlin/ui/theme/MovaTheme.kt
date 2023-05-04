package ui.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.intl.Locale

internal object AppTheme {
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

    internal val strings: AppString
        @Composable
        @ReadOnlyComposable
        get() = LocalStrings.current
}

@Composable
internal fun MovaTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    colors: AppColor = if (isDarkTheme) darkColors else lightColors,
    typography: AppTypography = if (isDarkTheme) darkTypography else lightTypography,
    dimensions: AppDimens = AppTheme.dimens,
    shapes: AppShape = AppTheme.shapes,
    strings: AppString = AppTheme.strings,
    content: @Composable () -> Unit
) {
    val rippleIndication = rememberRipple()
    CompositionLocalProvider(
        LocalColors provides colors,
        LocalDimens provides dimensions,
        LocalTypography provides typography,
        LocalShapes provides shapes,
        LocalIndication provides rippleIndication,
        LocalStrings provides strings,
        content = content
    )
}