package ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.CoreGraphics.CGRectGetHeight
import platform.UIKit.UIDevice
import platform.UIKit.UIScreen
import ui.screen.auth.AuthScreenState
import ui.screen.socialLogin.SocialLoginScreenState
import ui.theme.AppTheme
import ui.util.isXFamilyDevice

internal class IosArm64MainPlatform: Platform {

    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion

    private val isXFamilyIPhone: Boolean = UIScreen.mainScreen.bounds.let { bounds ->
        CGRectGetHeight(bounds)
    }.isXFamilyDevice() && UIDevice.currentDevice.model.startsWith(prefix = "iPhone")

    override val navigationBarInsetDp: Dp
        @Composable
        get() = if (isXFamilyIPhone) AppTheme.dimens.screenPadding else 0.dp

    override val statusBarInsetDp: Dp
        @Composable
        get() = if (isXFamilyIPhone) AppTheme.dimens.screenPadding * 2 else AppTheme.dimens.contentPadding

    override val imeBottomInsetDp: Dp
        @Composable
        get() = 0.dp
}

internal actual fun getPlatform(): Platform = IosArm64MainPlatform()

actual val uiModule: List<Module> = buildList {
    add(
        element = module {
            single <Platform> { IosArm64MainPlatform() }
            factory { SocialLoginScreenState() }
            factory { params ->
                AuthScreenState(
                    screenType = params[0],
                    loginWithEmailAndPassword = get(),
                    registerWithEmailAndPassword = get()
                )
            }
        }
    )
    add(element = commonUiModule)
}