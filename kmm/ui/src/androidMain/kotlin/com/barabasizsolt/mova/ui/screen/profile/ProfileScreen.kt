package com.barabasizsolt.mova.ui.screen.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.barabasizsolt.mova.domain.usecase.auth.LogOutUseCase
import com.barabasizsolt.mova.ui.catalog.MovaButton
import com.barabasizsolt.mova.ui.theme.AppTheme
import org.koin.compose.koinInject

@Composable
fun ProfileScreen() {
    val logOutUseCase = koinInject<LogOutUseCase>()

    Box(modifier = Modifier.fillMaxSize()) {
        MovaButton(
            text = "Logout",
            onClick = { logOutUseCase() },
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .padding(horizontal = AppTheme.dimens.screenPadding)
        )
    }
}