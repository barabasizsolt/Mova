package com.barabasizsolt.navigation.di

import com.barabasizsolt.explore.ExploreScreenModel
import com.barabasizsolt.home.HomeScreenModel
import com.barabasizsolt.login.LoginScreenModel
import org.koin.dsl.module

fun createScreenModules() = module {
    factory {
        LoginScreenModel(
            getIntentForGoogleAccountLogin = get(),
            loginWithGoogleAccountUseCase = get()
        )
    }
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