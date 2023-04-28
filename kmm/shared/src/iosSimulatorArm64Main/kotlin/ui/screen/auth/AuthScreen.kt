package ui.screen.auth

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

internal actual class AuthScreen actual constructor(private val screenType: String)  : Screen, KoinComponent {

    private val screenState: AuthScreenState by inject { parametersOf(screenType) }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        AuthScreenWrapper(
            state = screenState.state,
            screenTitle = screenState.screenProperty?.screenTitle.orEmpty(),
            authButtonText = screenState.screenProperty?.authButtonText.orEmpty(),
            authFooterText = screenState.screenProperty?.authFooterText.orEmpty(),
            authFooterQuestion = screenState.screenProperty?.authFooterQuestion.orEmpty(),
            email = screenState.email,
            onEmailChange = screenState::onEmailChange,
            password = screenState.password,
            onPasswordChange = screenState::onPasswordChange,
            authenticate = screenState::authenticate,
            authenticateWithGoogle = { /*TODO: Implement it*/ },
            authenticateWithFacebook = { /*TODO: Implement it*/ },
            changeAuthScreen = screenState::changeAuthScreen,
            isLoading = screenState.state is BaseAuthScreenState.State.Loading,
            isEnabled = screenState.isAuthEnabled,
            onDismiss = screenState::resetState,
            onBackPressed = { navigator.pop() }
        )
    }
}

