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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.barabasizsolt.catalog.AuthScreenDelimiter
import com.barabasizsolt.catalog.MovaButton
import com.barabasizsolt.catalog.MovaSnackBar
import com.barabasizsolt.catalog.SocialAuthFooter
import com.barabasizsolt.catalog.SocialLoginOption
import com.barabasizsolt.login.R
import com.barabasizsolt.theme.attributes.AppTheme
import com.google.accompanist.insets.statusBarsPadding

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

    Box {
        ScreenContent(screenState = screenState, activityResultLauncher = loginWithGoogleAccountLauncher)
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
                    actionLabel = "Dismiss",
                    duration = SnackbarDuration.Long
                )
                screenState.resetState()
            }
        }
    )
}

@Composable
private fun ScreenContent(
    screenState: SocialLoginScreenState,
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
                onClick = {
                    screenState.loginWithFacebook()
                },
                modifier = Modifier.padding(bottom = AppTheme.dimens.contentPadding * 2)
            )
        }
        item {
            GoogleLoginOption(
                onClick = {
                    val intent = screenState.getIntentForGoogleLogin()
                    activityResultLauncher.launch(intent)
                }
            )
        }
        item {
            AuthScreenDelimiter(
                text = "or",
                modifier = Modifier.padding(vertical = AppTheme.dimens.contentPadding * 4)
            )
        }
        item {
            MovaButton(
                text = "Sign in with password",
                onClick = screenState::onSignInClicked,
                isLoading = screenState.state is SocialLoginScreenState.State.Loading,
                modifier = Modifier.padding(bottom = AppTheme.dimens.screenPadding),
            )
        }
        item {
            SocialAuthFooter(
                text = "Don't have an account?",
                clickableText = "Sign up",
                onSignUpClick = screenState::onSignUpClicked
            )
        }
    }
}

@Composable
private fun SocialLoginScreenLogo(
    modifier: Modifier = Modifier
) = Image(
    painter = painterResource(id = com.barabasizsolt.util.R.drawable.social_logo),
    contentDescription = null,
    contentScale = ContentScale.Crop,
    modifier = modifier.size(size = 260.dp)
)

@Composable
private fun SocialLoginScreenTitle(
    modifier: Modifier = Modifier
) = Text(
    text = "Let's you in",
    style = AppTheme.typography.h3,
    fontWeight = FontWeight.Bold,
    textAlign = TextAlign.Center,
    color = AppTheme.colors.onPrimary,
    modifier = modifier.fillMaxWidth()
)

@Composable
private fun GoogleLoginOption(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) = SocialLoginOption(
    onClick = onClick,
    text = "Continue with Google",
    iconId = R.drawable.google_ic,
    modifier = modifier.fillMaxWidth()
)

@Composable
private fun FacebookLoginOption(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) = SocialLoginOption(
    onClick = onClick,
    text = "Continue with Facebook",
    iconId = R.drawable.facebook_ic,
    modifier = modifier.fillMaxWidth()
)

