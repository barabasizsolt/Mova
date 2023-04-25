package ui.navigation.appNav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.barabasizsolt.api.AuthenticationState
import com.barabasizsolt.mova.domain.usecase.auth.IsLoggedInUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@Composable
fun rememberAppNavigationState(
    scope: CoroutineScope = rememberCoroutineScope(),
    isLoggedInUseCase: IsLoggedInUseCase = get(),
): AppNavigationState = remember {
    AppNavigationState(
        scope = scope,
        isLoggedInUseCase = isLoggedInUseCase
    )
}


class AppNavigationState(
    scope: CoroutineScope,
    private val isLoggedInUseCase: IsLoggedInUseCase
) {
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