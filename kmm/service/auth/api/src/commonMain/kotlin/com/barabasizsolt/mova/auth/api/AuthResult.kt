package com.barabasizsolt.mova.auth.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext

sealed class AuthResult {

    data class Success(val data: Any? = null) : AuthResult()

    data class Failure(val error: String) : AuthResult()

    data class Dismissed(val error: String? = null) : AuthResult()
}

fun authenticate(
    authFunction: suspend () -> dev.gitlive.firebase.auth.AuthResult,
    sideEffect: () -> Unit = { }
): Flow<AuthResult> = callbackFlow {
    try {
        withContext(Dispatchers.Default) { authFunction() }
        trySend(element = AuthResult.Success()).also { sideEffect() }
    } catch (exception: Exception) {
        trySend(element = AuthResult.Failure(error = exception.message.orEmpty()))
    }
    awaitClose { }
}
