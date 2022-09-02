package com.barabasizsolt.mova.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

internal val LocalTypography = staticCompositionLocalOf { AppTypography() }

data class AppTypography(
    val h1: TextStyle = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 96.sp,
        letterSpacing = (-1.5).sp
    ),
    val h2: TextStyle = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 60.sp,
        letterSpacing = (-0.5).sp
    ),
    val h3: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 48.sp,
        letterSpacing = 0.sp
    ),
    val h4: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp,
        letterSpacing = 0.25.sp
    ),
    val h5: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        letterSpacing = 0.sp
    ),
    val h6: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        letterSpacing = 0.15.sp
    ),
    val subtitle1: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.15.sp
    ),
    val subtitle2: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 0.1.sp
    ),
    val body1: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp
    ),
    val body2: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.25.sp
    ),
    val button: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 1.25.sp
    ),
    val caption: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.4.sp
    ),
    val overLine: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        letterSpacing = 0.4.sp
    )
)