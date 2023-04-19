package com.barabasizsolt.mova.review.implementation

import com.barabasizsolt.review.api.ReviewService
import org.koin.dsl.module

fun createReviewModule() = module {
    factory { ReviewRemoteSource(baseHttpClient = get()) }
    single<ReviewService> { ReviewServiceImpl(remoteSource = get()) }
}