package com.barabasizsolt.auth.socialLogin

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.barabasizsolt.catalog.AuthScreenDelimiter
import com.barabasizsolt.catalog.MovaButton
import com.barabasizsolt.catalog.MovaSnackBar
import com.barabasizsolt.catalog.SocialAuthFooter
import com.barabasizsolt.catalog.SocialLoginOption
import com.barabasizsolt.mova.ui.screen.auth.loginregister.R
import com.barabasizsolt.theme.AppTheme

@Composable
fun SocialLoginScreen(screenState: SocialLoginScreenState) {
    val loginWithGoogleAccountLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data
        if (result.resultCode == Activity.RESULT_OK && data != null) {
            screenState.loginWithGoogle(intent = data)
        }
    }
    val snackBarHostState = remember { SnackbarHostState() }
    val dismissText = stringResource(id = R.string.dismiss)

    Box {
        ScreenContent(
            loginWithFacebook = screenState::loginWithFacebook,
            getIntentForGoogleLogin = screenState::getIntentForGoogleLogin,
            onSignInClicked = screenState::onSignInClicked,
            onSignUpClicked = screenState::onSignUpClicked,
            isLoading = screenState.state is SocialLoginScreenState.State.Loading,
            activityResultLauncher = loginWithGoogleAccountLauncher
        )
        MovaSnackBar(
            snackBarHostState = snackBarHostState,
            onDismiss = {
                snackBarHostState.currentSnackbarData?.dismiss()
                screenState.resetState()
            },
            modifier = Modifier.systemBarsPadding()
        )
    }

    LaunchedEffect(
        key1 = screenState.state,
        block = {
            if (screenState.state is SocialLoginScreenState.State.Error) {
                snackBarHostState.showSnackbar(
                    message = (screenState.state as SocialLoginScreenState.State.Error).message,
                    actionLabel = dismissText,
                    duration = SnackbarDuration.Long
                )
                screenState.resetState()
            }
        }
    )
}

@Composable
private fun ScreenContent(
    loginWithFacebook: () -> Unit,
    getIntentForGoogleLogin: () -> Intent,
    onSignInClicked: () -> Unit,
    onSignUpClicked: () -> Unit,
    isLoading: Boolean,
    activityResultLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppTheme.colors.primary)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(all = AppTheme.dimens.screenPadding)
    ) {
        item { SocialLoginScreenLogo() }
        item { SocialLoginScreenTitle(modifier = Modifier.padding(bottom = AppTheme.dimens.screenPadding * 2)) }
        item {
            FacebookLoginOption(
                onClick = loginWithFacebook,
                modifier = Modifier.padding(bottom = AppTheme.dimens.contentPadding * 2)
            )
        }
        item {
            GoogleLoginOption(
                onClick = { activityResultLauncher.launch(getIntentForGoogleLogin()) }
            )
        }
        item {
            AuthScreenDelimiter(
                text = stringResource(id = R.string.or),
                modifier = Modifier.padding(vertical = AppTheme.dimens.contentPadding * 4)
            )
        }
        item {
            MovaButton(
                text = stringResource(id = R.string.sign_in_with_password),
                onClick = onSignInClicked,
                isLoading = isLoading,
                modifier = Modifier.padding(bottom = AppTheme.dimens.screenPadding),
            )
        }
        item {
            SocialAuthFooter(
                text = stringResource(id = R.string.dont_have_an_account),
                clickableText = stringResource(id = R.string.sign_up),
                onSignUpClick = onSignUpClicked
            )
        }
    }
}

@Composable
private fun SocialLoginScreenLogo(
    modifier: Modifier = Modifier
) = Image(
    painter = painterResource(id = com.barabasizsolt.mova.ui.util.R.drawable.social_logo),
    contentDescription = null,
    contentScale = ContentScale.Crop,
    modifier = modifier.size(size = 260.dp)
)

@Composable
private fun SocialLoginScreenTitle(
    modifier: Modifier = Modifier
) = Text(
    text = stringResource(id = R.string.lets_you_in),
    style = AppTheme.typography.h3,
    fontWeight = FontWeight.Bold,
    textAlign = TextAlign.Center,
    modifier = modifier.fillMaxWidth()
)

@Composable
private fun GoogleLoginOption(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) = SocialLoginOption(
    onClick = onClick,
    text = stringResource(id = R.string.continue_with_google),
    iconId = R.drawable.google_ic,
    modifier = modifier.fillMaxWidth()
)

@Composable
private fun FacebookLoginOption(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) = SocialLoginOption(
    onClick = onClick,
    text = stringResource(id = R.string.continue_with_facebook),
    iconId = R.drawable.facebook_ic,
    modifier = modifier.fillMaxWidth()
)

