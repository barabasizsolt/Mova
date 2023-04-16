package com.barabasizsolt.network.implementation

import com.barabasizsolt.network.api.BaseHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class BaseHttpClientImpl(
    private val hostUrl: String,
    private val apiKey: String
) : BaseHttpClient {

    override val client: HttpClient
        get() = HttpClient(engineFactory = CIO) {
            install(
                plugin = ContentNegotiation,
                configure = {
                    json(
                        json = Json {
                            ignoreUnknownKeys = true /*Ignore the undefined DTO properties in the Json*/
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
        }
}