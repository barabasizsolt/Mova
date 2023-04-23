@file:JvmName("AndroidPlatformUnique")

package com.barabasizsolt.mova.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.barabasizsolt.mova.ui.screen.auth.loginRegister.AuthScreenState
import com.barabasizsolt.mova.ui.screen.auth.socialLogin.SocialLoginScreenState
import org.koin.core.module.Module
import org.koin.dsl.module

class AndroidPlatform : Platform {

    override val name: String = "Android"

    override val navigationBarInsetDp: Dp
        @Composable
        get() = with(LocalDensity.current) {
            WindowInsets.navigationBars.getBottom(density = this).toDp()
        }

    override val statusBarInsetDp: Dp
        @Composable
        get() = with(LocalDensity.current) {
            WindowInsets.statusBars.getTop(density = this).toDp()
        }

    override val imeBottomInsetDp: Dp
        @Composable
        get() = with(LocalDensity.current) {
            WindowInsets.ime.getBottom(density = this).toDp()
        }
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual val uiModule: List<Module> = buildList {
    add(
        element = module {
            single <Platform> { AndroidPlatform() }

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
        }
    )
    add(element = commonUiModule)
}