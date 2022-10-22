package com.barabasizsolt.auth.socialLogin

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.IdRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.barabasizsolt.catalog.MovaButton
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
    val scaffoldState = rememberScaffoldState()

    Scaffold(scaffoldState = scaffoldState) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppTheme.colors.primary)
                .padding(paddingValues = it)
                .navigationBarsPadding()
        ) {
            ScreenContent(
                screenState = screenState,
                activityResultLauncher = loginWithGoogleAccountLauncher
            )

            LaunchedEffect(
                key1 = screenState.state,
                block = {
                    if (screenState.state is SocialLoginScreenState.State.Error) {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "Oops, something went wrong.",
                            actionLabel = "Try again"
                        )
                    }
                }
            )
        }
    }
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
                onClick = {},
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
        item { SocialLoginDelimiter(modifier = Modifier.padding(vertical = AppTheme.dimens.contentPadding * 4)) }
        item {
            MovaButton(
                text = "Sign in with password",
                onClick = screenState::onAuthenticationClicked,
                isLoading = screenState.state is SocialLoginScreenState.State.Loading,
                modifier = Modifier.padding(bottom = AppTheme.dimens.screenPadding),
            )
        }
        item { SocialLoginFooter(onSignUpClick = {}) }
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
    modifier = modifier
)

@Composable
private fun FacebookLoginOption(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) = SocialLoginOption(
    onClick = onClick,
    text = "Continue with Facebook",
    iconId = R.drawable.facebook_ic,
    modifier = modifier
)

@Composable
private fun SocialLoginOption(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    @IdRes iconId: Int
) = OutlinedButton(
    border = BorderStroke(width = 1.dp, color = Color.LightGray.copy(alpha = if (isSystemInDarkTheme()) 0.4f else 1f)),
    shape = AppTheme.shapes.medium,
    onClick = onClick,
    colors = ButtonDefaults.buttonColors(
        backgroundColor = AppTheme.colors.background,
        contentColor = AppTheme.colors.onBackground
    ),
    contentPadding = PaddingValues(
        horizontal = AppTheme.dimens.contentPadding,
        vertical = AppTheme.dimens.contentPadding * 2
    ),
    modifier = modifier.fillMaxWidth()
) {
    Image(
        painter = painterResource(id = iconId),
        contentDescription = null
    )
    Spacer(modifier = Modifier.width(width = AppTheme.dimens.contentPadding * 2))
    Text(
        text = text,
        style = AppTheme.typography.body1,
        fontWeight = FontWeight.Bold,
        color = AppTheme.colors.onBackground,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}

@Composable
private fun SocialLoginDelimiter(
    modifier: Modifier = Modifier
) = Box(modifier = modifier.fillMaxWidth()) {
    Divider(
        color = Color.LightGray.copy(alpha = if (isSystemInDarkTheme()) 0.4f else 1f),
        modifier = Modifier.align(alignment = Alignment.Center)
    )
    Text(
        text = "or",
        style = AppTheme.typography.body1,
        fontWeight = FontWeight.Bold,
        color = if (isSystemInDarkTheme()) Color.White else Color.DarkGray,
        modifier = Modifier
            .background(color = AppTheme.colors.background)
            .padding(horizontal = AppTheme.dimens.contentPadding)
            .align(alignment = Alignment.Center)
    )
}

@Composable
private fun SocialLoginFooter(
    modifier: Modifier = Modifier,
    onSignUpClick: () -> Unit
) = Row(
    modifier = modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center
) {
    Text(
        text = "Don't have an account?",
        style = AppTheme.typography.body2,
        color = if (isSystemInDarkTheme()) Color.White else Color.DarkGray
    )
    Spacer(modifier = Modifier.width(width = AppTheme.dimens.contentPadding))
    Text(
        text = "Sign up",
        style = AppTheme.typography.body1,
        fontWeight = FontWeight.Bold,
        color = AppTheme.colors.secondary,
        modifier = Modifier
            .clickable { onSignUpClick() }
    )
}

