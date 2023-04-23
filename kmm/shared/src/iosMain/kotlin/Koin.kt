package com.barabasizsolt.mova.shared

import createAppModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(
            modules = createAppModule(
                host = "api.themoviedb.org",
                apiKey =  "93697a6983d40e793bc6b81401c77e1c",
                isDebugBuild = true
            )
        )
    }
}