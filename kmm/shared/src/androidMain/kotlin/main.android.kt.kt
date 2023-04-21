import androidx.compose.runtime.Composable
import com.barabasizsolt.mova.ui.navigation.appNav.AppNavigation
import com.barabasizsolt.mova.ui.navigation.appNav.TemporaryAppNavigation
import com.barabasizsolt.mova.ui.theme.MovaTheme

@Composable
fun EntryPoint() {
    MovaTheme {
        //AppNavigation()
        TemporaryAppNavigation()
    }
}