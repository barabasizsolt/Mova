import androidx.compose.runtime.Composable
import ui.navigation.appNav.AppNavigation
import ui.theme.MovaTheme

@Composable
fun EntryPoint() {
    MovaTheme {
        AppNavigation()
    }
}