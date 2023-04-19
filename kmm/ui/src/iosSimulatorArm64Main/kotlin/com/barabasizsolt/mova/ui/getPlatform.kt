package com.barabasizsolt.mova.ui

import androidx.compose.runtime.Composable
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.cache.memory.maxSizePercent
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.util.DebugLogger
import com.seiko.imageloader.util.LogPriority
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.UIKit.UIDevice

class IosSimulatorPlatform: Platform {

    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual val uiModule: Module = module {
    single <Platform> { IosSimulatorPlatform() }
}

@Composable
actual fun getImageLoader(): ImageLoader = ImageLoader {
    logger = DebugLogger(LogPriority.VERBOSE)
    components {
        setupDefaultComponents(imageScope = imageScope)
    }
    interceptor {
        memoryCacheConfig {
            maxSizePercent(percent = 0.25)
        }
    }
}