package com.barabasizsolt.mova.ui.catalog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

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
    val imageState by rememberImageState(imageUrl.orEmpty())
    val showShimmer = remember { mutableStateOf(value = true) }

    Box(
        modifier = modifier.background(shimmerBrush(targetValue = 1300f, showShimmer = showShimmer.value))
    ) {
        when (val state = imageState) {
            is ImageState.Error -> {
                if (disableShimmerOnError) showShimmer.value = false
                if (shouldShowFallbackOnError) {
                    Image(
                        painter = painterResource(res = fallbackResourcePath),
                        contentDescription = null,
                        alignment = alignment,
                        contentScale = contentScale,
                        modifier = modifier
                    )
                }
            }
            is ImageState.Success -> {
                showShimmer.value = false
                Image(
                    bitmap = state.bitmap,
                    contentDescription = null,
                    modifier = modifier,
                    alignment = alignment,
                    contentScale = contentScale
                )
            }
            is ImageState.Loading -> Unit
        }
    }
}