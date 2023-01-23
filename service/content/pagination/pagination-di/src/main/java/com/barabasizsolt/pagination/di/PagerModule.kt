package com.barabasizsolt.pagination.di

import com.barabasizsolt.pagination.api.Pager
import com.barabasizsolt.pagination.implementation.PagerImplementation
import org.koin.dsl.module

val pagerModule = module { factory<Pager> { PagerImplementation() } }