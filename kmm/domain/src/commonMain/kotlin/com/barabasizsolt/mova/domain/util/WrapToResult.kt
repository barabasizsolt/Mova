package com.barabasizsolt.mova.domain.util

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

inline fun <T> wrapToResult(function: () -> T): Result<T> = try {
    Result.Success(function())
} catch (exception: Exception) {
    Result.Failure(exception)
}

suspend fun asyncWrapToResult(functions: List<Result<out Any>>) : Result<Unit> = coroutineScope {
    val results = functions.map { function -> async { function } }.awaitAll()
    val exceptions = results.filterIsInstance<Result.Failure<Exception>>()
    return@coroutineScope if (exceptions.isEmpty()) Result.Success(data = Unit) else Result.Failure(exception = exceptions[0].exception)
}