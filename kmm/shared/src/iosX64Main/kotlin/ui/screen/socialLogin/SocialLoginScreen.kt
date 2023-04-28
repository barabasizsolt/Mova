package ui.screen.socialLogin

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.screen.auth.AuthScreen
import ui.screen.auth.ScreenType
import ui.screen.socialLogin.SocialLoginWrapper

actual object SocialLoginScreen : Screen, KoinComponent {

    private val screenState: SocialLoginScreenState by inject()

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        SocialLoginWrapper(
            state = screenState.state,
            loginWithGoogle = { /*TODO: Implement it*/ },
            loginWithFacebook = { /*TODO: Implement it*/ },
            onSignInClicked = { navigator.push(item = AuthScreen(screenType = ScreenType.LOGIN.name)) },
            onSignUpClicked = { navigator.push(item = AuthScreen(screenType = ScreenType.REGISTER.name)) },
            isLoading = screenState.state is BaseSocialLoginScreenState.State.Loading,
            onDismiss = screenState::resetState
        )
    }
}