package com.barabasizsolt.mova.ui.screen.auth.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.barabasizsolt.mova.ui.R
import com.barabasizsolt.mova.ui.catalog.GradientOverlay
import com.barabasizsolt.mova.ui.catalog.MovaButton
import com.barabasizsolt.mova.ui.theme.AppTheme
import com.barabasizsolt.mova.ui.util.withShadow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(screenState: WelcomeScreenState) {
    Box {
        Image(
            painter = painterResource(id = R.drawable.grid),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        GradientOverlay(colors = listOf(Color.Transparent, Color.Black))
        WelcomeScreenPager(
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = AppTheme.dimens.screenPadding * 3),
            onGetStartedClicked = screenState::navigateToAuth
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun WelcomeScreenPager(
    modifier: Modifier = Modifier,
    onGetStartedClicked: () -> Unit
) {
    val pagerState: PagerState = rememberPagerState()
    val pagerContent: List<WelcomeScreenPagerData> = WelcomeScreenPagerData.getPagerData()

    LaunchedEffect(Unit) {
        while (true) {
            delay(timeMillis = 5000)
            if (pagerState.pageCount > 0) {
                pagerState.animateScrollToPage(page = (pagerState.currentPage + 1) % (pagerState.pageCount))
            }
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.screenPadding)
    ) {
        HorizontalPager(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth(),
            count = pagerContent.size,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = AppTheme.dimens.screenPadding)
        ) { page ->
            WelcomeScreenPagerContent(pagerData = pagerContent[page])
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            activeColor = AppTheme.colors.secondary,
            inactiveColor = Color.White,
        )
        MovaButton(
            text = stringResource(id = R.string.get_started),
            onClick = onGetStartedClicked,
            modifier = Modifier.padding(horizontal = AppTheme.dimens.screenPadding)
        )
    }
}

@Composable
private fun WelcomeScreenPagerContent(
    modifier: Modifier = Modifier,
    pagerData: WelcomeScreenPagerData
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.dimens.screenPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.screenPadding)
    ) {
        Text(
            text = pagerData.title,
            style = AppTheme.typography.h4.withShadow(),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = pagerData.subtitle,
            style = AppTheme.typography.subtitle2.withShadow(),
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

private data class WelcomeScreenPagerData(val title: String, val subtitle: String) {
    companion object {
        @Composable
        fun getPagerData() = listOf(
            WelcomeScreenPagerData(
                title = stringResource(id = R.string.page_title1),
                subtitle = stringResource(id = R.string.page_subtitle1)
            ),
            WelcomeScreenPagerData(
                title = stringResource(id = R.string.page_title2),
                subtitle = stringResource(id = R.string.page_subtitle2)
            ),
            WelcomeScreenPagerData(
                title = stringResource(id = R.string.page_title3),
                subtitle = stringResource(id = R.string.page_subtitle3)
            )
        )
    }
}