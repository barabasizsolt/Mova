package ui.navigation.appNav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.barabasizsolt.mova.auth.api.AuthenticationState
import com.barabasizsolt.mova.domain.usecase.auth.IsLoggedInUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Composable
internal fun rememberAppNavigationState(
    scope: CoroutineScope = rememberCoroutineScope(),
): AppNavigationState = remember { AppNavigationState(scope = scope) }


internal class AppNavigationState(scope: CoroutineScope) : KoinComponent {

    private val isLoggedInUseCase: IsLoggedInUseCase by inject()
    var authState by mutableStateOf<AuthenticationState?>(value = null)
        private set

    init {
        scope.launch {
            isLoggedInUseCase().collect {
                authState = it
            }
        }
    }
}