package com.barabasizsolt.domain.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

inline fun <T> wrapToResult(function: () -> T): Result<T> = try {
    Result.Success(function())
} catch (exception: Exception) {
    Result.Failure(exception)
}

suspend fun asyncWrapToResult(scope: CoroutineScope, functions: List<Result<out Any>>) : Result<Unit> {
    val results = functions.map { function -> scope.async { function } }.awaitAll()
    val exception = results.filterIsInstance<Result.Failure<Exception>>()
    return if (exception.isEmpty()) Result.Success(data = Unit) else Result.Failure(exception = exception[0].exception)
}