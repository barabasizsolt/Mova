package ui.screen.socialLogin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.component.KoinComponent
import ui.catalog.AuthScreenDelimiter
import ui.catalog.BackButton
import ui.catalog.MovaButton
import ui.catalog.MovaSnackBar
import ui.catalog.SocialAuthFooter
import ui.catalog.SocialLoginOption
import ui.getPlatform
import ui.theme.AppTheme

internal expect object SocialLoginScreen : Screen, KoinComponent

@Composable
internal fun SocialLoginWrapper(
    state: BaseSocialLoginScreenState.State,
    loginWithFacebook: () -> Unit,
    loginWithGoogle: () -> Unit,
    onSignInClicked: () -> Unit,
    onSignUpClicked: () -> Unit,
    onDismiss: () -> Unit,
    isLoading: Boolean,
    onBackPressed: () -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val actionLabel = AppTheme.strings.dismiss

    Box {
        ScreenContent(
            loginWithFacebook = loginWithFacebook,
            loginWithGoogle = loginWithGoogle,
            onSignInClicked = onSignInClicked,
            onSignUpClicked = onSignUpClicked,
            isLoading = isLoading
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
            if (state is BaseSocialLoginScreenState.State.Error) {
                snackBarHostState.showSnackbar(
                    message = state.message,
                    actionLabel = actionLabel,
                    duration = SnackbarDuration.Long
                )
                onDismiss()
            }
        }
    )
}

@Composable
private fun ScreenContent(
    loginWithFacebook: () -> Unit,
    loginWithGoogle: () -> Unit,
    onSignInClicked: () -> Unit,
    onSignUpClicked: () -> Unit,
    isLoading: Boolean
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppTheme.colors.primary)
            .padding(top = getPlatform().statusBarInsetDp),
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
            GoogleLoginOption(onClick = loginWithGoogle)
        }
        item {
            AuthScreenDelimiter(
                text = AppTheme.strings.or,
                modifier = Modifier.padding(vertical = AppTheme.dimens.contentPadding * 4)
            )
        }
        item {
            MovaButton(
                text = AppTheme.strings.signInWithPassword,
                onClick = onSignInClicked,
                isLoading = isLoading,
                modifier = Modifier.padding(bottom = AppTheme.dimens.screenPadding),
            )
        }
        item {
            SocialAuthFooter(
                text = AppTheme.strings.noAccount,
                clickableText = AppTheme.strings.signUp,
                onSignUpClick = onSignUpClicked
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun SocialLoginScreenLogo(
    modifier: Modifier = Modifier
) = Image(
    painter = painterResource(res = "drawable/social_logo.png"),
    contentDescription = null,
    contentScale = ContentScale.Crop,
    modifier = modifier.size(size = 260.dp)
)

@Composable
private fun SocialLoginScreenTitle(
    modifier: Modifier = Modifier
) = Text(
    text = AppTheme.strings.letsYouIn,
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
    text = AppTheme.strings.continueWithGoogle,
    path = "drawable/google_ic.xml",
    modifier = modifier.fillMaxWidth()
)

@Composable
private fun FacebookLoginOption(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) = SocialLoginOption(
    onClick = onClick,
    text = AppTheme.strings.continueWithFacebook,
    path = "drawable/facebook_ic.xml",
    modifier = modifier.fillMaxWidth()
)
