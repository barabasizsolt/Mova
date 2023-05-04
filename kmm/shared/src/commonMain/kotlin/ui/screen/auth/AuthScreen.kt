package ui.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.component.KoinComponent
import ui.catalog.AuthInputField
import ui.catalog.AuthScreenDelimiter
import ui.catalog.BackButton
import ui.catalog.MovaButton
import ui.catalog.MovaSnackBar
import ui.catalog.SocialAuthFooter
import ui.catalog.SocialLoginOption
import ui.getPlatform
import ui.theme.AppTheme

internal expect class AuthScreen(screenType: String) : Screen, KoinComponent

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun AuthScreenWrapper(
    state: BaseAuthScreenState.State,
    authScreenType: BaseAuthScreenState.AuthScreenType,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    authenticate: () -> Unit,
    authenticateWithFacebook: () -> Unit,
    authenticateWithGoogle: () -> Unit,
    changeAuthScreen: () -> Unit,
    isLoading: Boolean,
    isEnabled: Boolean,
    onDismiss: () -> Unit,
    onBackPressed: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val snackBarHostState = remember { SnackbarHostState() }
    val actionLabel = AppTheme.strings.dismiss
    val screenTitle = when (authScreenType) {
        is BaseAuthScreenState.AuthScreenType.Login -> AppTheme.strings.loginToYourAccount
        is BaseAuthScreenState.AuthScreenType.Register -> AppTheme.strings.createYourAccount
    }
    val authButtonText = when (authScreenType) {
        is BaseAuthScreenState.AuthScreenType.Login -> AppTheme.strings.signIn
        is BaseAuthScreenState.AuthScreenType.Register -> AppTheme.strings.signUp
    }
    val authFooterQuestion = when (authScreenType) {
        is BaseAuthScreenState.AuthScreenType.Login -> AppTheme.strings.noAccount
        is BaseAuthScreenState.AuthScreenType.Register -> AppTheme.strings.alreadyHaveAnAccount
    }
    val authFooterText = when (authScreenType) {
        is BaseAuthScreenState.AuthScreenType.Login -> AppTheme.strings.signUp
        is BaseAuthScreenState.AuthScreenType.Register -> AppTheme.strings.signIn
    }

    Box(modifier = Modifier.background(color = AppTheme.colors.primary)) {
        ScreenContent(
            screenTitle = screenTitle,
            authButtonText = authButtonText,
            authFooterText = authFooterText,
            authFooterQuestion = authFooterQuestion,
            email = email,
            onEmailChange = onEmailChange,
            password = password,
            onPasswordChange = onPasswordChange,
            authenticate = authenticate,
            authenticateWithFacebook = authenticateWithFacebook,
            authenticateWithGoogle = authenticateWithGoogle,
            changeAuthScreen = changeAuthScreen,
            isLoading = isLoading,
            isEnabled = isEnabled,
            keyboardController = keyboardController
        )

        BackButton(
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .padding(
                    horizontal = AppTheme.dimens.screenPadding,
                    vertical = getPlatform().statusBarInsetDp + AppTheme.dimens.screenPadding
                ),
            onBackPressed = onBackPressed
        )

        MovaSnackBar(
            snackBarHostState = snackBarHostState,
            onDismiss = {
                snackBarHostState.currentSnackbarData?.dismiss()
                onDismiss()
            },
            modifier = Modifier.padding(bottom = getPlatform().navigationBarInsetDp)
        )
    }

    LaunchedEffect(
        key1 = state,
        block = {
            if (state is BaseAuthScreenState.State.Error) {
                snackBarHostState.showSnackbar(
                    message = state.message,
                    actionLabel = actionLabel
                )
                onDismiss()
            }
        }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun ScreenContent(
    screenTitle: String,
    authButtonText: String,
    authFooterText: String,
    authFooterQuestion: String,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    authenticate: () -> Unit,
    authenticateWithFacebook: () -> Unit,
    authenticateWithGoogle: () -> Unit,
    changeAuthScreen: () -> Unit,
    isLoading: Boolean,
    isEnabled: Boolean,
    keyboardController: SoftwareKeyboardController?
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = getPlatform().statusBarInsetDp,
                bottom = getPlatform().imeBottomInsetDp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(all = AppTheme.dimens.screenPadding)
    ) {
        item {
            AuthScreenLogo(
                modifier = Modifier.padding(
                    top = AppTheme.dimens.screenPadding * 4,
                    bottom = AppTheme.dimens.screenPadding
                )
            )
        }
        item {
            AuthScreenTitle(
                title = screenTitle,
                modifier = Modifier.padding(bottom = AppTheme.dimens.screenPadding * 2)
            )
        }
        item {
            EmailInput(
                email = email,
                onEmailChange = onEmailChange,
                modifier = Modifier.padding(bottom = AppTheme.dimens.screenPadding)
            )
        }
        item {
            PasswordInput(
                password = password,
                onPasswordChange = onPasswordChange,
                keyboardActions = {
                    authenticate()
                    keyboardController?.hide()
                },
                modifier = Modifier.padding(bottom = AppTheme.dimens.screenPadding * 2)
            )
        }
        item {
            MovaButton(
                text = authButtonText,
                onClick = {
                    authenticate()
                    keyboardController?.hide()
                },
                isLoading = isLoading,
                isEnabled = isEnabled
            )
        }
        item {
            AuthScreenDelimiter(
                text = AppTheme.strings.orContinueWith,
                modifier = Modifier.padding(vertical = AppTheme.dimens.contentPadding * 4)
            )
        }
        item {
            SocialItemHolder(
                onGoogleClicked = authenticateWithGoogle,
                onFacebookClicked = authenticateWithFacebook,
                modifier = Modifier.padding(bottom = AppTheme.dimens.contentPadding * 3)
            )
        }
        item {
            SocialAuthFooter(
                text = authFooterQuestion,
                clickableText = authFooterText,
                onSignUpClick = changeAuthScreen
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun AuthScreenLogo(
    modifier: Modifier = Modifier
) = Image(
    painter = painterResource(res = "drawable/mova_logo.png"),
    contentDescription = null,
    contentScale = ContentScale.Crop,
    modifier = modifier.size(size = 100.dp)
)

@Composable
private fun AuthScreenTitle(
    title: String,
    modifier: Modifier = Modifier
) = Text(
    text = title,
    style = AppTheme.typography.h5,
    fontWeight = FontWeight.Bold,
    textAlign = TextAlign.Center,
    modifier = modifier.fillMaxWidth()
)

@Composable
private fun EmailInput(
    modifier: Modifier = Modifier,
    email: String,
    onEmailChange: (String) -> Unit
) = AuthInputField(
    value = email,
    onValueChange = onEmailChange,
    placeholder = AppTheme.strings.email,
    leadingIcon = Icons.Default.Email,
    keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Email,
        imeAction = ImeAction.Next,
        capitalization = KeyboardCapitalization.None
    ),
    modifier = modifier
)

@Composable
private fun PasswordInput(
    modifier: Modifier = Modifier,
    password: String,
    onPasswordChange: (String) -> Unit,
    keyboardActions: (() -> Unit) = { }
) {
    var passwordVisible by rememberSaveable { mutableStateOf(value = false) }

    AuthInputField(
        value = password,
        onValueChange = onPasswordChange,
        placeholder = AppTheme.strings.password,
        leadingIcon = Icons.Default.Lock,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = { keyboardActions() }),
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = null,
                    tint = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
                    modifier = Modifier.size(size = 20.dp)
                )
            }
        },
        modifier = modifier
    )
}

@Composable
private fun SocialItemHolder(
    modifier: Modifier = Modifier,
    onGoogleClicked: () -> Unit,
    onFacebookClicked: () -> Unit
) = Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically
) {
    SocialLoginOption(
        onClick = onFacebookClicked,
        path = "drawable/facebook_ic.xml"
    )
    Spacer(modifier = Modifier.width(width = AppTheme.dimens.contentPadding * 2))
    SocialLoginOption(
        onClick = onGoogleClicked,
        path = "drawable/google_ic.xml"
    )
}