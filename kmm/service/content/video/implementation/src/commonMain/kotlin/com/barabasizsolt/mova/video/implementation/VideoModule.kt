package com.barabasizsolt.mova.video.implementation

import com.barabasizsolt.mova.video.api.VideoService
import org.koin.dsl.module

fun createVideoModule() = module {
    factory { VideoRemoteSource(baseHttpClient = get()) }
    single<VideoService> { VideoServiceImpl(remoteSource = get()) }
}