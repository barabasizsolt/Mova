package com.barabasizsolt.mova.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import org.koin.core.module.Module

interface Platform {
    val name: String
    @get:Composable val navigationBarInsetDp: Dp
    @get:Composable val statusBarInsetDp: Dp
    @get:Composable val imeBottomInsetDp: Dp
}

expect fun getPlatform(): Platform

expect val uiModule: Module