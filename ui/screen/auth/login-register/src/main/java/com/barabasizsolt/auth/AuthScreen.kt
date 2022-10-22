package com.barabasizsolt.auth

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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.barabasizsolt.catalog.AuthInputField
import com.barabasizsolt.catalog.AuthScreenDelimiter
import com.barabasizsolt.catalog.MovaButton
import com.barabasizsolt.catalog.SocialAuthFooter
import com.barabasizsolt.theme.attributes.AppTheme
import com.barabasizsolt.util.R
import com.google.accompanist.insets.statusBarsPadding
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import com.barabasizsolt.catalog.MovaSnackBar
import com.barabasizsolt.catalog.SocialLoginOption
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(screenState: AuthScreenState) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { scaffoldState.snackbarHostState }
    ) {
        Box(modifier = Modifier.padding(paddingValues = it)) {
            ScreenContent(screenState = screenState)
            MovaSnackBar(
                snackBarHostState = scaffoldState.snackbarHostState,
                onDismiss = {
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                    screenState.resetState()
                },
                modifier = Modifier.align(alignment = Alignment.BottomCenter)
            )
        }

        when (val state = screenState.state) {
            is AuthScreenState.State.Error -> {
                println("Here")
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = state.message,
                        actionLabel = "Try again"
                    )
                    screenState.resetState()
                }
            }
            else -> Unit
        }
    }
}

@Composable
private fun ScreenContent(screenState: AuthScreenState) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppTheme.colors.primary)
            .statusBarsPadding()
            .imePadding(),
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
                title = screenState.screenProperty?.screenTitle.orEmpty(),
                modifier = Modifier.padding(bottom = AppTheme.dimens.screenPadding * 2)
            )
        }
        item {
            EmailInput(
                email = screenState.email,
                onEmailChange = screenState::onEmailChange,
                modifier = Modifier.padding(bottom = AppTheme.dimens.screenPadding)
            )
        }
        item {
            PasswordInput(
                password = screenState.password,
                onPasswordChange = screenState::onPasswordChange,
                keyboardActions = screenState::authenticate,
                modifier = Modifier.padding(bottom = AppTheme.dimens.screenPadding * 2)
            )
        }
        item {
            MovaButton(
                text = screenState.screenProperty?.authButtonText.orEmpty(),
                onClick = screenState::authenticate,
                isLoading = screenState.state is AuthScreenState.State.Loading,
                isEnabled = screenState.isAuthEnabled
            )
        }
        item {
            AuthScreenDelimiter(
                text = "or continue with",
                modifier = Modifier.padding(vertical = AppTheme.dimens.contentPadding * 4)
            )
        }
        item {
            SocialItemHolder(
                onGoogleClicked = { },
                onFacebookClicked = { },
                modifier = Modifier.padding(bottom = AppTheme.dimens.contentPadding * 3)
            )
        }
        item {
            SocialAuthFooter(
                text = "Don't have an account?",
                clickableText = screenState.screenProperty?.authFooterText.orEmpty(),
                onSignUpClick = screenState::changeAuthScreen
            )
        }
    }
}

@Composable
private fun AuthScreenLogo(
    modifier: Modifier = Modifier
) = Image(
    painter = painterResource(id = R.drawable.mova_logo),
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
    color = AppTheme.colors.onPrimary,
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
    placeholder = "Email",
    leadingIcon = Icons.Default.Email,
    keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Email,
        imeAction = ImeAction.Next
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
        placeholder = "Password",
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
    SocialLoginOption(onClick = onFacebookClicked, iconId = com.barabasizsolt.login.R.drawable.facebook_ic)
    Spacer(modifier = Modifier.width(width = AppTheme.dimens.contentPadding * 2))
    SocialLoginOption(onClick = onGoogleClicked, iconId = com.barabasizsolt.login.R.drawable.google_ic)
}