package com.barabasizsolt.mova.shared.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.CoreGraphics.CGRectGetHeight
import platform.UIKit.UIDevice
import platform.UIKit.UIScreen
import ui.commonUiModule
import ui.theme.AppTheme
import ui.util.isXFamilyDevice
import ui.Platform

internal class IOSPlatform: Platform {

    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion

    private val isXFamilyIPhone: Boolean = UIScreen.mainScreen.bounds.let { bounds ->
        CGRectGetHeight(bounds)
    }.isXFamilyDevice() && UIDevice.currentDevice.model.startsWith(prefix = "iPhone")

    override val navigationBarInsetDp: Dp
        @Composable
        get() = if (isXFamilyIPhone) AppTheme.dimens.screenPadding else 0.dp

    override val statusBarInsetDp: Dp
        @Composable
        get() = if (isXFamilyIPhone) AppTheme.dimens.screenPadding * 2 else AppTheme.dimens.contentPadding

    override val imeBottomInsetDp: Dp
        @Composable
        get() = 0.dp
}

//actual fun getPlatform(): Platform = IOSPlatform()
//
//actual val uiModule: List<Module> = buildList {
//    add(
//        element = module {
//            single <Platform> { IOSPlatform() }
//        }
//    )
//    add(element = commonUiModule)
//}