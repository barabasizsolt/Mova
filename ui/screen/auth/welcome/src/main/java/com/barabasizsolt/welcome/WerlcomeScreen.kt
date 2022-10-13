package com.barabasizsolt.welcome

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.barabasizsolt.catalog.GradientOverlay
import com.barabasizsolt.catalog.MovaButton
import com.barabasizsolt.theme.attributes.AppTheme
import com.barabasizsolt.util.withShadow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
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
                .padding(bottom = AppTheme.dimens.screenPadding * 2),
            onGetStartedClicked = screenState::navigateToLogin
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
        MovaButton(
            text = "Get Started", onClick = onGetStartedClicked,
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
        fun getPagerData() = listOf(
            WelcomeScreenPagerData(
                title = "Welcome to Mova",
                subtitle = "The best movie streaming app of the century to make your day great!"
            ),
            WelcomeScreenPagerData(
                title = "Lorem Ipsum",
                subtitle = "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
            ),
            WelcomeScreenPagerData(
                title = "Ipsum Lorem",
                subtitle = "Morbi tempus consequat lectus, quis tincidunt diam efficitur non."
            )
        )
    }
}