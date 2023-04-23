package com.barabasizsolt.mova.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.UIKit.UIDevice

class IosSimulatorPlatform: Platform {

    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion

    override val navigationBarInsetDp: Dp
        @Composable
        get() = 0.dp

    override val statusBarInsetDp: Dp
        @Composable
        get() = 0.dp

    override val imeBottomInsetDp: Dp
        @Composable
        get() = 0.dp
}

actual fun getPlatform(): Platform = IosSimulatorPlatform()

actual val uiModule: List<Module> = buildList {
    add(element = module { single <Platform> { IosSimulatorPlatform() } })
    add(element = commonUiModule)
}