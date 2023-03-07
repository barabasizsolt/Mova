package com.barabasizsolt.filter.di

import com.barabasizsolt.filter.api.FilterService
import com.barabasizsolt.filter.implementation.FilterServiceImpl
import org.koin.dsl.module

fun createFilterModule() = module {
    single<FilterService> { FilterServiceImpl() }
}