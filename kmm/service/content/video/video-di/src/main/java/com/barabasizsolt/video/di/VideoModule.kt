package com.barabasizsolt.video.di

import com.barabasizsolt.api.RetrofitClient
import com.barabasizsolt.video.api.VideoService
import com.barabasizsolt.video.implementation.VideoNetworkService
import com.barabasizsolt.video.implementation.VideoRemoteSource
import com.barabasizsolt.video.implementation.VideoServiceImpl
import org.koin.dsl.module

fun createVideoModule() = module {
    factory { get<RetrofitClient>().sessionless.create(VideoNetworkService::class.java) }
    factory { VideoRemoteSource(networkService = get()) }
    single<VideoService> { VideoServiceImpl(remoteSource = get()) }
}