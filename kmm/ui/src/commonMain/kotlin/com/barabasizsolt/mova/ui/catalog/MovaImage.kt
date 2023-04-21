package com.barabasizsolt.mova.ui.catalog

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale

@Composable
expect fun MovaImage(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    shouldShowFallbackOnError: Boolean = false,
    fallbackResourcePath: String = "drawable/ic_default_profile.xml",
    disableShimmerOnError: Boolean = false
)