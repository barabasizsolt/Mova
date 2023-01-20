package com.barabasizsolt.domain.util.result

sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Failure<T>(val exception: Throwable) : Result<T>()
}