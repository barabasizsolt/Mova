package com.barabasizsolt.mova.ui

import platform.UIKit.UIDevice

class IosSimulatorPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IosSimulatorPlatform()