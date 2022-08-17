package com.barabasizsolt.api

import retrofit2.Retrofit

interface RetrofitClient {
    val session: Retrofit
    val sessionless: Retrofit
}