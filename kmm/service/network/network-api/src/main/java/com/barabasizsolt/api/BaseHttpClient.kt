package com.barabasizsolt.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get

interface BaseHttpClient {
    val client: HttpClient

}

suspend inline fun <reified T> BaseHttpClient.get(
    urlString: String,
    block: HttpRequestBuilder.() -> Unit = {}
) = client.get(urlString = urlString, block = block).body<T>()