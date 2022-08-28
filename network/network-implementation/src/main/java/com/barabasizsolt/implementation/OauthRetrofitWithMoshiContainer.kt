package com.barabasizsolt.implementation

import com.barabasizsolt.api.RetrofitClient
import com.halcyonmobile.oauth.dependencies.AuthenticationLocalStorage
import com.halcyonmobile.oauth.dependencies.SessionExpiredEventHandler
import com.halcyonmobile.oauthmoshi.OauthRetrofitWithMoshiContainerBuilder
import com.halcyonmobile.oauthmoshi.RefreshTokenResponseWrapper
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class OauthRetrofitWithMoshiContainer(
    baseUrl: String,
    apiKey: String,
    clientId: String,
    provideAuthenticationLocalStorage: AuthenticationLocalStorage,
    provideSessionExpiredEventHandler: SessionExpiredEventHandler,
    okhttpBuilder: OkHttpClient.Builder.() -> OkHttpClient.Builder,
    moshiBuilder: Moshi.Builder.() -> Moshi.Builder,
) : RetrofitClient {

    private val oauth = OauthRetrofitWithMoshiContainerBuilder(
        clientId = clientId,
        authenticationLocalStorage = provideAuthenticationLocalStorage,
        sessionExpiredEventHandler = provideSessionExpiredEventHandler,
    )
        .configureRetrofit {
            baseUrl(baseUrl)
        }
        .configureBothOkHttpClient {
            readTimeout(1L, TimeUnit.MINUTES)
            connectTimeout(30L, TimeUnit.SECONDS)
            this.okhttpBuilder().addInterceptor { chain ->
                val original = chain.request()
                val url = original.url().newBuilder().apply {
                    addQueryParameter("api_key", apiKey)
                }.build()
                chain.proceed(original.newBuilder().url(url).build())
            }
        }
        .configureMoshi {
            moshiBuilder().apply {
                add(RefreshTokenResponseWrapper())
            }
        }
        .build()

    override val session: Retrofit
        get() = oauth.oauthRetrofitContainer.sessionRetrofit
    override val sessionless: Retrofit
        get() = oauth.oauthRetrofitContainer.sessionlessRetrofit
}