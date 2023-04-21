package com.barabasizsolt.mova.ui

import org.koin.core.module.Module
import org.koin.dsl.module
import platform.UIKit.UIDevice

class IosSimulatorPlatform: Platform {

    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual val uiModule: Module = module {
    single <Platform> { IosSimulatorPlatform() }
}