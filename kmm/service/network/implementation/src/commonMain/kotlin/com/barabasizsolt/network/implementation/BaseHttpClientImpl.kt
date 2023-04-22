package com.barabasizsolt.network.implementation

import com.barabasizsolt.network.api.BaseHttpClient
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

class BaseHttpClientImpl(
    private val hostUrl: String,
    private val apiKey: String,
    private val isDebugBuild: Boolean
) : BaseHttpClient {
    @OptIn(ExperimentalSerializationApi::class)
    override val client: HttpClient
        get() = HttpClient {
            install(
                plugin = ContentNegotiation,
                configure = {
                    json(
                        json = Json {
                            ignoreUnknownKeys = true /*Ignore the undefined DTO properties in the Json*/
                            isLenient = true
                            explicitNulls = false
                        }
                    )
                }
            )
            install(
                plugin = DefaultRequest,
                configure = {
                    url {
                        protocol = URLProtocol.HTTPS
                        host = hostUrl
                        path("3/")
                        parameters.append(name = "api_key", value = apiKey)
                    }
                }
            )
            if (isDebugBuild) {
                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            Napier.v(message = message, throwable = null, tag = "BaseHttpClient::")
                        }
                    }
                    level = LogLevel.BODY
                }
            }
        }.also { Napier.base(antilog = DebugAntilog()) }
}