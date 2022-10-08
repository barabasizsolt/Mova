package com.barabasizsolt.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.barabasizsolt.catalog.MovaButton
import com.barabasizsolt.theme.attributes.AppTheme

@Composable
fun ProfileScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        MovaButton(
            text = "Logout",
            onClick = { },
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .padding(horizontal = AppTheme.dimens.screenPadding)
        )
    }
}