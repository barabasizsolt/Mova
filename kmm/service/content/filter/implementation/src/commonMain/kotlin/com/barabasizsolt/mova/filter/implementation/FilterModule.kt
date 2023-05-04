package com.barabasizsolt.mova.filter.implementation


import com.barabasizsolt.mova.filter.api.FilterService
import org.koin.dsl.module

fun createFilterModule() = module {
    single<FilterService> { FilterServiceImpl() }
}