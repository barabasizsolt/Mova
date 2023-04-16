package com.barabasizsolt.network.implementation

import com.barabasizsolt.network.api.BaseHttpClient
import org.koin.dsl.module

fun createNetworkModule(
    baseUrl: String,
    apiKey: String
) = module {
    single<BaseHttpClient> { BaseHttpClientImpl(hostUrl = baseUrl, apiKey = apiKey) }
}