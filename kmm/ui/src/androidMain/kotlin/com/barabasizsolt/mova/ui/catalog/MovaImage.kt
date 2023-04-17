package com.barabasizsolt.mova.ui.catalog

import android.annotation.SuppressLint
import androidx.annotation.IdRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.SubcomposeAsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.barabasizsolt.mova.ui.R
import com.barabasizsolt.mova.ui.util.isSvg

@SuppressLint("ResourceType")
@Composable
fun MovaImage(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    shouldShowFallbackOnError: Boolean = false,
    @IdRes fallbackResourceId: Int = R.drawable.ic_default_profile,
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
                    painter = painterResource(id = fallbackResourceId),
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