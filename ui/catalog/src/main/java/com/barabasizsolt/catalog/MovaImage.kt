package com.barabasizsolt.catalog

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.barabasizsolt.util.isSvg

@Composable
fun MovaImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    placeHolderColor: Color = Color.LightGray
) {
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(data = imageUrl)
            .let { if (imageUrl.isSvg()) it.decoderFactory(factory = SvgDecoder.Factory()) else it }
            .build(),
        placeholder = ColorPainter(color = placeHolderColor),
        contentDescription = null,
        alignment = alignment,
        contentScale = contentScale,
        modifier = modifier
    )
}