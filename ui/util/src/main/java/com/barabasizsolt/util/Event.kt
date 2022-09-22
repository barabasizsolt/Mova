package com.barabasizsolt.util

class Event<T : Any>(private val data: T) {

    private var consumed: Boolean = false

    fun consume(): T? = data.takeUnless { consumed }?.also { consumed = true }

    fun peek() = data
}