package com.barabasizsolt.network
import com.barabasizsolt.api.RetrofitClient
import com.barabasizsolt.implementation.OauthRetrofitWithMoshiContainer
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import org.koin.core.scope.Scope
import org.koin.dsl.module

fun createNetworkModule(
    baseUrl: String,
    apiKey: String,
    clientId: String,
    okhttpBuilderProvider: Scope.() -> (OkHttpClient.Builder.() -> OkHttpClient.Builder) = { { this } },
    moshiBuilderProvider: Scope.() -> (Moshi.Builder.() -> Moshi.Builder) = { { this } },
) = listOf(
    module {
        single<RetrofitClient> {
            OauthRetrofitWithMoshiContainer(
                baseUrl = baseUrl,
                apiKey = apiKey,
                clientId = clientId,
                provideAuthenticationLocalStorage = get(),
                provideSessionExpiredEventHandler = get(),
                okhttpBuilder = okhttpBuilderProvider(),
                moshiBuilder = moshiBuilderProvider(),
            )
        }
    }
)

