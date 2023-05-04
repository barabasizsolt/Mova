package ui.catalog

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
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.util.isSvg

@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun MovaImage(
    modifier: Modifier,
    imageUrl: String?,
    alignment: Alignment,
    contentScale: ContentScale,
    shouldShowFallbackOnError: Boolean,
    fallbackResourcePath: String,
    disableShimmerOnError: Boolean
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