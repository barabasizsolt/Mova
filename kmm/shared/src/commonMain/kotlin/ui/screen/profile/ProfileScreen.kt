package ui.screen.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.barabasizsolt.mova.domain.usecase.auth.LogOutUseCase
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.catalog.MovaButton
import ui.theme.AppTheme

internal object ProfileScreen : Tab, KoinComponent {

    private val logOutUseCase by inject<LogOutUseCase>()

    override val options: TabOptions
        @Composable
        get()  {
            val icon = rememberVectorPainter(Icons.Default.Person)
            return remember { TabOptions(index = 3u, title = "Profile", icon = icon) }
        }

    @Composable
    override fun Content() {
        val scope = rememberCoroutineScope()

        Box(modifier = Modifier.fillMaxSize()) {
            MovaButton(
                text = "Logout",
                onClick = { scope.launch { logOutUseCase() } },
                modifier = Modifier
                    .align(alignment = Alignment.Center)
                    .padding(horizontal = AppTheme.dimens.screenPadding)
            )
        }
    }
}