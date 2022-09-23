package com.barabasizsolt.login

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import com.barabasizsolt.catalog.MovaButton
import com.barabasizsolt.theme.attributes.AppTheme

object LoginScreen  : Screen {

    @Composable
    override fun Content() {
        val screenModel = getScreenModel<LoginScreenModel>()
        val tabNavigator = LocalTabNavigator.current

        val loginWithGoogleAccountLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
            if (result.resultCode == Activity.RESULT_OK && data != null) {
                screenModel.loginWithGoogle(intent = data)
            }
        }

        /*TODO: resolve navigation between modules*/
//        when(screenModel.action) {
//            is LoginScreenModel.Action.NavigateToHome -> tabNavigator.current =
//            is LoginScreenModel.Action.NavigateToRegister -> TODO()
//            else -> Unit
//        }

        Box(modifier = Modifier.fillMaxSize()) {
            MovaButton(
                text = "Login",
                onClick = {
                    val intent = screenModel.getIntentForGoogleLogin()
                    loginWithGoogleAccountLauncher.launch(intent)
                },
                modifier = Modifier
                    .align(alignment = Alignment.Center)
                    .padding(horizontal = AppTheme.dimens.screenPadding)
            )
        }
    }
}