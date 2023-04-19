package com.barabasizsolt.mova.domain.util

sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Failure<T>(val exception: Throwable) : Result<T>()
}