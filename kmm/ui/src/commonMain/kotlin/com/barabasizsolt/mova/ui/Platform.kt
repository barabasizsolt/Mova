package com.barabasizsolt.mova.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import com.barabasizsolt.mova.ui.screen.detail.DetailScreenState
import com.barabasizsolt.mova.ui.screen.explore.ExploreScreenState
import com.barabasizsolt.mova.ui.screen.explore.FilterScreenState
import com.barabasizsolt.mova.ui.screen.home.HomeScreenState
import com.barabasizsolt.mova.ui.screen.seeall.SeeAllScreenState
import org.koin.core.module.Module
import org.koin.dsl.module

interface Platform {
    val name: String
    @get:Composable val navigationBarInsetDp: Dp
    @get:Composable val statusBarInsetDp: Dp
    @get:Composable val imeBottomInsetDp: Dp
}

expect fun getPlatform(): Platform

expect val uiModule: List<Module>

val commonUiModule: Module = module {
    // State holder

    // [HomeScreenState]
    factory {
        HomeScreenState(
            getHomeScreen = get(),
            getHomeScreenFlow = get()
        )
    }
    // [ExploreScreenState]
    factory {
        ExploreScreenState(
            discoverContentUseCase = get(),
            discoverContentFlowUseCase = get(),
            searchContentUseCase = get(),
            searchContentFlowUseCase = get(),
            deleteContentUseCase = get(),
            getGenresUseCase = get(),
            filterService = get()
        )
    }
    // [FilterScreenState]
    single {
        FilterScreenState(
            getGenresFlowUseCase = get(),
            filterService = get()
        )
    }
    // [SeeAllScreenState]
    factory { params ->
        SeeAllScreenState(
            contentType = params[0],
            getSeeAllScreenUseCase = get(),
            getSeeAllScreenFlowUseCase = get()
        )
    }
    // [DetailScreenState]
    factory { params ->
        DetailScreenState(
            id = params[0],
            getMovieDetailsUseCase = get()
        )
    }
}