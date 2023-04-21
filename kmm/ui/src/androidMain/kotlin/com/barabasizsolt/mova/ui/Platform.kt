package com.barabasizsolt.mova.ui

import com.barabasizsolt.mova.ui.screen.auth.loginRegister.AuthScreenState
import com.barabasizsolt.mova.ui.screen.auth.socialLogin.SocialLoginScreenState
import org.koin.core.module.Module
import org.koin.dsl.module

class AndroidPlatform : Platform {

    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual val uiModule: Module = module {
    single <Platform> { AndroidPlatform() }

    // State holder
    // [SocialLoginScreen]
    factory {
        SocialLoginScreenState(
            getIntentForGoogleAccountLogin = get(),
            loginWithGoogleAccountUseCase = get(),
            loginWithFacebookAccountUseCase = get()
        )
    }
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
}