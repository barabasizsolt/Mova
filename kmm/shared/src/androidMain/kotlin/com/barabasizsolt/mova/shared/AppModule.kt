package com.barabasizsolt.mova.shared

import com.barabasizsolt.activityprovider.di.activityProviderModule
import com.barabasizsolt.mova.domain.di.createAndroidDomainModules
import com.barabasizsolt.mova.ui.uiModule
import com.barabasizsolt.network.implementation.createNetworkModule

actual fun createAppModule(
    host: String,
    apiKey: String,
    isDebugBuild: Boolean
) = buildList {
    add(activityProviderModule)
    add(createNetworkModule(host = host, apiKey = apiKey, isDebugBuild = isDebugBuild))
    addAll(createAndroidDomainModules())
    add(uiModule)
}