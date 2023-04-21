package com.barabasizsolt.mova.ui

import androidx.compose.runtime.Composable
import com.seiko.imageloader.ImageLoader
import org.koin.core.module.Module

interface Platform {
    val name: String
}

expect val uiModule: Module