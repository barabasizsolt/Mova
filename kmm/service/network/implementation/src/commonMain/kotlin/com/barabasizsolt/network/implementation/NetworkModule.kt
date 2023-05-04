package com.barabasizsolt.network.implementation

import com.barabasizsolt.network.api.BaseHttpClient
import org.koin.dsl.module

fun createNetworkModule(
    host: String,
    apiKey: String,
    isDebugBuild: Boolean
) = module {
    single<BaseHttpClient> { BaseHttpClientImpl(hostUrl = host, apiKey = apiKey, isDebugBuild = isDebugBuild) }
}