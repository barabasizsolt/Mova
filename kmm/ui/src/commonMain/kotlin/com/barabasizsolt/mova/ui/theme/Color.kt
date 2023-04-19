package com.barabasizsolt.mova.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val Jam = Color(color = 0xFF60100B)
private val Black200 = Color(color = 0xFF121212)
private val Pink = Color(color = 0xFFCF6679)

internal val LocalColors = staticCompositionLocalOf { lightColors }

val lightColors = lightColors()
val darkColors = darkColors()

@Immutable
data class AppColor(
    val primary: Color,
    val primaryVariant: Color,
    val secondary: Color,
    val secondaryVariant: Color,
    val background: Color,
    val surface: Color,
    val error: Color,
    val onPrimary: Color,
    val onSecondary: Color,
    val onBackground: Color,
    val onSurface: Color,
    val onError: Color
)

fun lightColors(
    primary: Color = Color.White,
    primaryVariant: Color = Color.White,
    secondary: Color = Color.Red,
    secondaryVariant: Color = Jam,
    background: Color = Color.White,
    surface: Color = Color.White,
    error: Color = Color.Red,
    onPrimary: Color = Color.Black,
    onSecondary: Color = Color.White,
    onBackground: Color = Color.Black,
    onSurface: Color = Color.Black,
    onError: Color = Color.Red,
): AppColor = AppColor(
    primary = primary,
    primaryVariant = primaryVariant,
    secondary = secondary,
    secondaryVariant = secondaryVariant,
    background = background,
    surface = surface,
    error = error,
    onPrimary = onPrimary,
    onSecondary = onSecondary,
    onBackground = onBackground,
    onSurface = onSurface,
    onError = onError
)

fun darkColors(
    primary: Color = Color.Black,
    primaryVariant: Color = Color.Black,
    secondary: Color = Color.Red,
    secondaryVariant: Color = Jam,
    background: Color = Black200,
    surface: Color = Black200,
    error: Color = Pink,
    onPrimary: Color = Color.White,
    onSecondary: Color = Color.White,
    onBackground: Color = Color.White,
    onSurface: Color = Color.White,
    onError: Color = Color.Black
): AppColor = AppColor(
    primary = primary,
    primaryVariant = primaryVariant,
    secondary = secondary,
    secondaryVariant = secondaryVariant,
    background = background,
    surface = surface,
    error = error,
    onPrimary = onPrimary,
    onSecondary = onSecondary,
    onBackground = onBackground,
    onSurface = onSurface,
    onError = onError
)