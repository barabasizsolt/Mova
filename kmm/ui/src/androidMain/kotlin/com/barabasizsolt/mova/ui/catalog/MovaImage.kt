package com.barabasizsolt.mova.ui.catalog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.SubcomposeAsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.barabasizsolt.mova.ui.util.isSvg
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
    val showShimmer = remember { mutableStateOf(value = true) }
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(data = imageUrl)
            .let { if (imageUrl?.isSvg() == true) it.decoderFactory(factory = SvgDecoder.Factory()) else it }
            .build(),
        error = {
            if (shouldShowFallbackOnError) {
                Image(
                    painter = painterResource(res = fallbackResourcePath),
                    contentDescription = null,
                    alignment = alignment,
                    contentScale = contentScale,
                    modifier = modifier
                )
            }
        },
        onSuccess = { showShimmer.value = false },
        onError = { if (disableShimmerOnError) showShimmer.value = false },
        contentDescription = null,
        alignment = alignment,
        contentScale = contentScale,
        modifier = modifier.background(shimmerBrush(targetValue = 1300f, showShimmer = showShimmer.value))
    )
}