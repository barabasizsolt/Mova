package com.barabasizsolt.login

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.barabasizsolt.catalog.MovaButton
import com.barabasizsolt.theme.attributes.AppTheme

@Composable
fun LoginScreen(screenState: LoginScreenState) {
    val loginWithGoogleAccountLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val data = result.data
        if (result.resultCode == Activity.RESULT_OK && data != null) {
            screenState.loginWithGoogle(intent = data)
        }
    }

    when(screenState.state) {
        is LoginScreenState.State.Loading -> {
            //TODO: implement it
        }
        else -> ScreenContent(
            screenState = screenState,
            activityResultLauncher = loginWithGoogleAccountLauncher
        )
    }

    when(screenState.state) {
        is LoginScreenState.State.Error -> {
            //TODO: Implement it
        }
        else -> Unit
    }
}

@Composable
private fun ScreenContent(
    screenState: LoginScreenState,
    activityResultLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>
) {
    Box(modifier = Modifier.fillMaxSize()) {
        MovaButton(
            text = "Login",
            onClick = {
                val intent = screenState.getIntentForGoogleLogin()
                activityResultLauncher.launch(intent)
            },
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .padding(horizontal = AppTheme.dimens.screenPadding)
        )
    }
}