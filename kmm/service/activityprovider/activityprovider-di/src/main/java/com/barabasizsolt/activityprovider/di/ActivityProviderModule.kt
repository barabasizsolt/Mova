package com.barabasizsolt.activityprovider.di

import com.barabasizsolt.activityprovider.api.ActivityProvider
import com.barabasizsolt.activityprovider.api.ActivitySetter
import com.barabasizsolt.activityprovider.implementation.ActivityProviderImp
import org.koin.core.module.Module
import org.koin.dsl.module

val activityProviderModule: Module = module {
    single<ActivityProvider> { get<ActivityProviderImp>() }
    single<ActivitySetter> { get<ActivityProviderImp>() }
    single { ActivityProviderImp() }
}
