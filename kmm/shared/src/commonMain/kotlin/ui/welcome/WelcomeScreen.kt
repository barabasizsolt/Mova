package ui.welcome

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.component.KoinComponent
import ui.catalog.GradientOverlay
import ui.catalog.HorizontalPagerIndicator
import ui.catalog.MovaButton
import ui.getPlatform
import ui.screen.socialLogin.SocialLoginScreen
import ui.theme.AppTheme
import ui.util.withShadow

internal object WelcomeScreen: Screen, KoinComponent {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val navigator: Navigator = LocalNavigator.currentOrThrow

        Box {
            Image(
                painter = painterResource(res = "drawable/grid.png"),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            GradientOverlay(colors = listOf(Color.Transparent, Color.Black))
            WelcomeScreenPager(
                modifier = Modifier
                    .align(alignment = Alignment.BottomCenter)
                    .padding(bottom = AppTheme.dimens.screenPadding * 3 + getPlatform().navigationBarInsetDp),
                onGetStartedClicked = {
                    navigator.push(item = SocialLoginScreen)
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
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
            if (pagerContent.isNotEmpty()) {
                pagerState.animateScrollToPage(page = (pagerState.currentPage + 1) % (pagerContent.size))
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
            pageCount = pagerContent.size,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = AppTheme.dimens.screenPadding)
        ) { page ->
            WelcomeScreenPagerContent(pagerData = pagerContent[page])
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            activeColor = AppTheme.colors.secondary,
            inActiveColor = Color.White,
            indicatorCount = pagerContent.size,
            itemCount = pagerContent.size
        )
        MovaButton(
            text = "Get Started",
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