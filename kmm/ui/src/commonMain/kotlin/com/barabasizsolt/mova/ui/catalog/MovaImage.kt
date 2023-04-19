package com.barabasizsolt.mova.ui.catalog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.seiko.imageloader.rememberAsyncImagePainter
import com.seiko.imageloader.AsyncImagePainter
import com.seiko.imageloader.ImageRequestState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MovaImage(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    shouldShowFallbackOnError: Boolean = false,
    fallbackResourcePath: String = "drawable/ic_default_profile.xml",
    disableShimmerOnError: Boolean = false
) {
    var showShimmer by remember { mutableStateOf(value = true) }
    val requestPainter: AsyncImagePainter = rememberAsyncImagePainter(imageUrl.orEmpty())
    var isVisible by remember { mutableStateOf(value = true) }

    when (requestPainter.requestState) {
        is ImageRequestState.Failure -> {
            if (disableShimmerOnError) showShimmer = false
            if (shouldShowFallbackOnError) {
                isVisible = false
                Image(
                    painter = painterResource(res = fallbackResourcePath),
                    contentDescription = null,
                    alignment = alignment,
                    contentScale = contentScale,
                    modifier = modifier
                )
            }
        }
        is ImageRequestState.Loading -> Unit
        is ImageRequestState.Success -> { showShimmer = false }
    }

    if (isVisible) {
        Image(
            painter = requestPainter,
            contentDescription = null,
            alignment = alignment,
            contentScale = contentScale,
            modifier = modifier.background(shimmerBrush(targetValue = 1300f, showShimmer = showShimmer))
        )
    }
}