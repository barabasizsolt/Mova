package com.barabasizsolt.mova.ui

import com.barabasizsolt.mova.ui.screen.auth.loginRegister.AuthScreenState
import com.barabasizsolt.mova.ui.screen.auth.socialLogin.SocialLoginScreenState
import com.barabasizsolt.mova.ui.screen.detail.DetailScreenState
import com.barabasizsolt.mova.ui.screen.explore.ExploreScreenState
import com.barabasizsolt.mova.ui.screen.explore.FilterScreenState
import com.barabasizsolt.mova.ui.screen.home.HomeScreenState
import com.barabasizsolt.mova.ui.screen.seeall.SeeAllScreenState
import org.koin.core.module.Module
import org.koin.dsl.module

class AndroidPlatform : Platform {

    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual val uiModule: Module = module {
    single <Platform> { AndroidPlatform() }

    // State holder

    // [SocialLoginScreenState]
    factory {
        SocialLoginScreenState(
            getIntentForGoogleAccountLogin = get(),
            loginWithGoogleAccountUseCase = get(),
            loginWithFacebookAccountUseCase = get()
        )
    }
    // [AuthScreenState]
    factory { params ->
        AuthScreenState(
            screenType = params[0],
            loginWithEmailAndPassword = get(),
            loginWithGoogleAccountUseCase = get(),
            loginWithFacebookAccountUseCase = get(),
            getIntentForGoogleAccountLogin = get(),
            registerWithEmailAndPassword = get()
        )
    }
    // [HomeScreenState]
    factory {
        HomeScreenState(
            getHomeScreen = get(),
            getHomeScreenFlow = get()
        )
    }
    // [ExploreScreenState]
    factory {
        ExploreScreenState(
            discoverContentUseCase = get(),
            discoverContentFlowUseCase = get(),
            searchContentUseCase = get(),
            searchContentFlowUseCase = get(),
            deleteContentUseCase = get(),
            getGenresUseCase = get(),
            filterService = get()
        )
    }
    // [FilterScreenState]
    single {
        FilterScreenState(
            getGenresFlowUseCase = get(),
            filterService = get()
        )
    }
    // [SeeAllScreenState]
    factory { params ->
        SeeAllScreenState(
            contentType = params[0],
            getSeeAllScreenUseCase = get(),
            getSeeAllScreenFlowUseCase = get()
        )
    }
    // [DetailScreenState]
    factory { params ->
        DetailScreenState(
            id = params[0],
            getMovieDetailsUseCase = get()
        )
    }
}