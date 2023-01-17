package com.barabasizsolt.base

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

abstract class BaseScreenState : CoroutineScope {

    final override val coroutineContext = SupervisorJob() + Dispatchers.Main
    val scope = CoroutineScope(context = coroutineContext)

    var state by mutableStateOf<State>(value = State.Normal)
        protected set

    abstract fun getScreenData(swipeRefresh: Boolean)

    fun onClear() = coroutineContext.cancelChildren()

    sealed class State {
        object Normal : State()
        object Loading : State()
        object SwipeRefresh : State()
        data class Error(val message: String) : State()
        object ShowSnackBar : State()
    }

    companion object {
        inline fun <reified T : BaseScreenState> getSaver(
            noinline save: SaverScope.(value: T) -> Map<String, Any?>,
            noinline restore: (Map<String, Any?>) -> T?
        ) : Saver<T, *> = mapSaver(save = save, restore = restore)
    }
}