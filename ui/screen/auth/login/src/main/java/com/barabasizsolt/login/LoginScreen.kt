package com.barabasizsolt.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.barabasizsolt.catalog.MovaButton
import com.barabasizsolt.theme.attributes.AppTheme

object LoginScreen  : Screen {

    @Composable
    override fun Content() {
        Box(modifier = Modifier.fillMaxSize()) {
            MovaButton(
                text = "Login",
                onClick = {  },
                modifier = Modifier
                    .align(alignment = Alignment.Center)
                    .padding(horizontal = AppTheme.dimens.screenPadding)
            )
        }
    }
}