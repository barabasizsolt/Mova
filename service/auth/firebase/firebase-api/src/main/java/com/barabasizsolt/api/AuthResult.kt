package com.barabasizsolt.api

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

sealed class AuthResult {

    object Success : AuthResult()

    data class Failure(val error: String) : AuthResult()

    data class Dismissed(val error: String? = null) : AuthResult()
}

fun <T>consumeTask(
    task: Task<T>,
    sideEffect: () -> Unit = { }
): Flow<AuthResult> = callbackFlow {
    task
        .addOnSuccessListener {
            trySend(element = AuthResult.Success).isSuccess
            sideEffect()
        }
        .addOnFailureListener { error -> trySend(element = AuthResult.Failure(error = error.message.orEmpty())).isFailure }
    awaitClose { }
}

sealed class AuthWithResult<T> {

    data class Success<T>(val data: T) : AuthWithResult<T>()

    data class Failure<T>(val error: String) : AuthWithResult<T>()

    data class Dismissed<T>(val message: String? = null) : AuthWithResult<T>()
}

fun <P, T>consumeTaskWithResult(task: Task<T>, taskConverter: (T) -> P): Flow<AuthWithResult<P>> = callbackFlow {
    task
        .addOnSuccessListener { result -> trySend(element = AuthWithResult.Success(data = taskConverter(result))).isSuccess }
        .addOnFailureListener { error -> trySend(element = AuthWithResult.Failure(error = error.message.orEmpty())).isFailure  }
    awaitClose { }
}
