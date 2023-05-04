package ui.screen.auth

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

internal actual class AuthScreen actual constructor(private val screenType: String) : Screen, KoinComponent {

    private val screenState: AuthScreenState by inject { parametersOf(screenType) }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val loginWithGoogleAccountLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val data = result.data
            if (result.resultCode == Activity.RESULT_OK && data != null) {
                screenState.authenticateWithGoogle(intent = data)
            }
        }

        AuthScreenWrapper(
            state = screenState.state,
            authScreenType = screenState.authScreenType,
            email = screenState.email,
            onEmailChange = screenState::onEmailChange,
            password = screenState.password,
            onPasswordChange = screenState::onPasswordChange,
            authenticate = screenState::authenticate,
            authenticateWithGoogle = { loginWithGoogleAccountLauncher.launch(screenState.getIntentForGoogleLogin()) },
            authenticateWithFacebook = screenState::authenticateWithFacebook,
            changeAuthScreen = screenState::changeAuthScreen,
            isLoading = screenState.state is BaseAuthScreenState.State.Loading,
            isEnabled = screenState.isAuthEnabled,
            onDismiss = screenState::resetState,
            onBackPressed = { navigator.pop() }
        )

        /*TODO: Add later*/
//        BeagleModules(modules = createBeagleModules { user ->
//            screenState.onEmailChange(user.email)
//            screenState.onPasswordChange(user.password)
//            screenState.authenticate()
//        })
    }
}

