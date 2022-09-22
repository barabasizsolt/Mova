package com.barabasizsolt.theme.attributes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

internal val LocalColors = staticCompositionLocalOf { lightColors() }

class AppColor(
    primary: Color,
    primaryVariant: Color,
    secondary: Color,
    secondaryVariant: Color,
    background: Color,
    surface: Color,
    error: Color,
    onPrimary: Color,
    onSecondary: Color,
    onBackground: Color,
    onSurface: Color,
    onError: Color,
    isLight: Boolean
) {
    var primary by mutableStateOf(value = primary)
        private set
    var primaryVariant by mutableStateOf(value = primaryVariant)
        private set
    var secondary by mutableStateOf(value = secondary)
        private set
    var secondaryVariant by mutableStateOf(value = secondaryVariant)
        private set
    var background by mutableStateOf(value = background)
        private set
    var surface by mutableStateOf(value = surface)
        private set
    var error by mutableStateOf(value = error)
        private set
    var onPrimary by mutableStateOf(value = onPrimary)
        private set
    var onSecondary by mutableStateOf(value = onSecondary)
        private set
    var onBackground by mutableStateOf(value = onBackground)
        private set
    var onSurface by mutableStateOf(value = onSurface)
        private set
    var onError by mutableStateOf(value = onError)
        private set
    var isLight by mutableStateOf(value = isLight)
        internal set

    fun copy(
        primary: Color = this.primary,
        primaryVariant: Color = this.primaryVariant,
        secondary: Color = this.secondary,
        secondaryVariant: Color = this.secondaryVariant,
        background: Color = this.background,
        surface: Color = this.surface,
        error: Color = this.error,
        onPrimary: Color = this.onPrimary,
        onSecondary: Color = this.onSecondary,
        onBackground: Color = this.onBackground,
        onSurface: Color = this.onSurface,
        onError: Color = this.onError,
        isLight: Boolean = this.isLight
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
        onError = onError,
        isLight = isLight
    )

    fun updateColorsFrom(other: AppColor) {
        primary = other.primary
        primaryVariant = other.primaryVariant
        secondary = other.secondary
        secondaryVariant = other.secondaryVariant
        background = other.background
        surface = other.surface
        error = other.error
        onPrimary = other.onPrimary
        onSecondary = other.onSecondary
        onBackground = other.onBackground
        onSurface = other.onSurface
        onError = other.onError
        isLight = other.isLight
    }
}

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
    onError = onError,
    isLight = true
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
    onError = onError,
    isLight = true
)

private val Jam = Color(color = 0xFF60100B)
private val Black200 = Color(color = 0xFF121212)
private val Pink = Color(color = 0xFFCF6679)