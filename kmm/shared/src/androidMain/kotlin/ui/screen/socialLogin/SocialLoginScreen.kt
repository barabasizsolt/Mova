package ui.screen.socialLogin

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.screen.auth.AuthScreen
import ui.screen.auth.ScreenType

actual object SocialLoginScreen : Screen, KoinComponent {

    private val screenState: SocialLoginScreenState by inject()

    @Composable
    override fun Content() {
        val loginWithGoogleAccountLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val data = result.data
            if (result.resultCode == Activity.RESULT_OK && data != null) {
                screenState.loginWithGoogle(intent = data)
            }
        }
        val navigator = LocalNavigator.currentOrThrow

        SocialLoginWrapper(
            state = screenState.state,
            loginWithFacebook = screenState::loginWithFacebook,
            loginWithGoogle = { loginWithGoogleAccountLauncher.launch(screenState.getIntentForGoogleLogin()) },
            onSignInClicked = { navigator.push(item = AuthScreen(screenType = ScreenType.LOGIN.name)) },
            onSignUpClicked = { navigator.push(item = AuthScreen(screenType = ScreenType.REGISTER.name)) },
            isLoading = screenState.state is BaseSocialLoginScreenState.State.Loading,
            onDismiss = screenState::resetState
        )
    }
}