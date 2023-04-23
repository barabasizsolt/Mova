package com.barabasizsolt.mova.ui

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.UIKit.UIDevice

class IOSPlatform: Platform {

    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion

    override val navigationBarInsetDp: Dp
        get() = 0.dp

    override val statusBarInsetDp: Dp
        get() = 0.dp

    override val imeBottomInsetDp: Dp
        get() = 0.dp
}

actual fun getPlatform(): Platform = IOSPlatform()

actual val uiModule: Module = module {
    single <Platform> { IOSPlatform() }
}