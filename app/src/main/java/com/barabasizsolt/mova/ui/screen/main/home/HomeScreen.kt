package com.barabasizsolt.mova.ui.screen.main.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource

import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.barabasizsolt.mova.R

object HomeScreen  : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(id = R.string.home_tab)
            val icon = rememberVectorPainter(image = Icons.Default.Home)
            return remember { TabOptions(index = 0u, title = title, icon = icon) }
        }

    @Composable
    override fun Content() {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Home",
                style = MaterialTheme.typography.h3,
                modifier = Modifier.align(alignment = Alignment.Center)
            )
        }
    }
}