package ui.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

@Composable
fun LifecycleEvent(onStateChanged: (Lifecycle.Event) -> Unit) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val stateCallback by rememberUpdatedState(newValue = onStateChanged)

    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            stateCallback(event)
        }
        lifecycle.addObserver(observer)
        onDispose { lifecycle.removeObserver(observer) }
    }
}
