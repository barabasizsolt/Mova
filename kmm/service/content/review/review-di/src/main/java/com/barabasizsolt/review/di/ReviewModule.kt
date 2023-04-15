package com.barabasizsolt.review.di

import com.barabasizsolt.api.RetrofitClient
import com.barabasizsolt.review.api.ReviewService
import com.barabasizsolt.review.implementation.ReviewNetworkService
import com.barabasizsolt.review.implementation.ReviewRemoteSource
import com.barabasizsolt.review.implementation.ReviewServiceImpl
import org.koin.dsl.module

fun createReviewModule() = module {
    factory { get<RetrofitClient>().sessionless.create(ReviewNetworkService::class.java) }
    factory { ReviewRemoteSource(networkService = get()) }
    single<ReviewService> { ReviewServiceImpl(remoteSource = get()) }
}