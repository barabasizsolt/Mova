package com.barabasizsolt.mova.ui.screen.base

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseScreenState : CoroutineScope {

    final override val coroutineContext = SupervisorJob() + Dispatchers.Main
    val scope = CoroutineScope(context = coroutineContext)

    var state by mutableStateOf<State>(value = State.Normal)
        protected set

    abstract fun getScreenData(userAction: UserAction, delay: Long = 0)

    fun onClear() = coroutineContext.cancelChildren()

    fun <T> Flow<T>.observe(action: suspend (T) -> Unit) = this.onEach(action).launchIn(scope = scope)

    sealed class State {
        object Normal : State()
        object Loading : State()
        object SwipeRefresh : State()
        object SearchLoading : State()
        data class Error(val message: String) : State()
        object ShowSnackBar : State()
        object TryAgainLoading : State()
    }

    companion object {
        inline fun <reified T : BaseScreenState> getBaseSaver(
            noinline save: SaverScope.(value: T) -> Map<String, Any?>,
            noinline restore: (Map<String, Any?>) -> T?
        ) : Saver<T, *> = mapSaver(save = save, restore = restore)
    }
}