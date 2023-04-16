package com.barabasizsolt.mova.di

import com.barabasizsolt.activityprovider.di.activityProviderModule
import com.barabasizsolt.auth.createAuthModules
import com.barabasizsolt.di.createNetworkModule
import com.barabasizsolt.di.createNetworkModule1
import com.barabasizsolt.domain.di.createDomainModules
import com.barabasizsolt.mova.BuildConfig

fun createAppModule() = buildList {
    add(activityProviderModule)
    add(createNetworkModule(baseUrl = BuildConfig.BASE_URL, apiKey = BuildConfig.API_KEY, clientId = BuildConfig.CLIENT_ID))
    add(createNetworkModule1(host = BuildConfig.HOST, apiKey = BuildConfig.API_KEY))
    addAll(createAuthModules())
    addAll(createDomainModules())
}