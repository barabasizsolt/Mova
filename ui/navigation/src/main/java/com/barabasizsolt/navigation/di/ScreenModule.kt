package com.barabasizsolt.navigation.di

import com.barabasizsolt.explore.ExploreScreenModel
import com.barabasizsolt.home.HomeScreenModel
import org.koin.dsl.module

fun createScreenModules() = module {
    factory {
        HomeScreenModel(
            getHomeScreen = get(),
            getHomeScreenFlow = get()
        )
    }
    factory {
        ExploreScreenModel(
            getExploreScreen = get(),
            getExploreScreenFlow = get()
        )
    }
}