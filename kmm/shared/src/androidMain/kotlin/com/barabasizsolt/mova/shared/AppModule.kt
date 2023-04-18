package com.barabasizsolt.mova.shared

import com.barabasizsolt.activityprovider.di.activityProviderModule
import com.barabasizsolt.mova.domain.di.createAndroidDomainModules
import com.barabasizsolt.network.implementation.createNetworkModule

fun createAndroidAppModule(
    host: String,
    apiKey: String
) = buildList {
    add(activityProviderModule)
    add(createNetworkModule(host = host, apiKey = apiKey))
    addAll(createAndroidDomainModules())
}