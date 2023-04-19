package com.barabasizsolt.mova.ui

import androidx.compose.runtime.Composable
import com.barabasizsolt.activityprovider.api.ActivityProvider
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.cache.memory.maxSizePercent
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.util.DebugLogger
import com.seiko.imageloader.util.LogPriority
import okio.Path.Companion.toOkioPath
import org.koin.compose.koinInject
import org.koin.core.module.Module
import org.koin.dsl.module

class AndroidPlatform() : Platform {

    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual val uiModule: Module = module {
    single <Platform> { AndroidPlatform() }
}
@Composable
actual fun getImageLoader(): ImageLoader {
    val provider: ActivityProvider = koinInject()
    return ImageLoader {
        logger = DebugLogger(logPriority = LogPriority.VERBOSE)
        components {
            setupDefaultComponents(context = provider.get())
        }
        interceptor {
            memoryCacheConfig {
                maxSizePercent(
                    context = provider.get(),
                    percent = 0.3
                )
            }
            diskCacheConfig {
                directory(provider.get().cacheDir.resolve("image_cache").toOkioPath())
                maxSizeBytes(size = 512L * 1024 * 1024) // 512MB
            }
        }
    }
}