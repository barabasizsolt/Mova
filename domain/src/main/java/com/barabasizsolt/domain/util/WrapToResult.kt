package com.barabasizsolt.domain.util

inline fun <T> wrapToResult(function: () -> T): Result<T> = try {
    Result.Success(function())
} catch (exception: Exception) {
    Result.Failure(exception)
}
