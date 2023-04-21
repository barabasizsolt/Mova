package com.barabasizsolt.mova.ui.catalog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.readBytes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.skia.Image

@Composable
fun rememberImageState(
    imageUrl: String,
) = produceState<ImageState>(initialValue = ImageState.Loading) {
    runCatching {
        ImageUtils.getImageBitmapByUrl(imageUrl)
    }.onSuccess {
        value = ImageState.Success(it)
    }.onFailure {
        value = ImageState.Error
    }
}

sealed class ImageState {
    object Loading : ImageState()
    object Error : ImageState()
    data class Success(val bitmap: ImageBitmap) : ImageState()
}

object ImageUtils {

    private val client = HttpClient()

    private val inMemoryCache = mutableMapOf<String, ByteArray>()

    suspend fun getImageBitmapByUrl(url: String): ImageBitmap {
        val bytes = inMemoryCache.getOrPut(url) {
            client.get(url).readBytes()
        }
        val bitmap = withContext(Dispatchers.Default) {
            Image.makeFromEncoded(bytes).toComposeImageBitmap()
        }
        return bitmap
    }
}