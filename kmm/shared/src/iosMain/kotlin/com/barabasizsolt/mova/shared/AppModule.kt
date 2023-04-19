package com.barabasizsolt.mova.shared

import com.barabasizsolt.mova.ui.uiModule
import com.barabasizsolt.network.implementation.createNetworkModule

actual fun createAppModule(
    host: String,
    apiKey: String,
    isDebugBuild: Boolean
) = buildList {
    add(createNetworkModule(host = host, apiKey = apiKey, isDebugBuild = isDebugBuild))
    //addAll(createAndroidDomainModules())
    add(uiModule)
}