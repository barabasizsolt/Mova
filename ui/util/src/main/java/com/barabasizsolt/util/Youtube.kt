package com.barabasizsolt.util

import android.annotation.SuppressLint
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.InternalComposeApi
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.google.accompanist.web.rememberWebViewStateWithHTMLData
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController

@OptIn(InternalComposeApi::class)
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun YoutubePlayer(
    videoId: String,
    modifier: Modifier = Modifier
) {
    val state = rememberWebViewStateWithHTMLData(
        data = videoId.getFrameVideo(),
        mimeType = "text/html",
        encoding = "utf-8"
    )

    WebView(
        state = state,
        onCreated = {
//            it.webViewClient = object : WebViewClient() {
//                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
//                    return false
//                }
//            }
            it.settings.javaScriptEnabled = true
        },
        modifier = modifier.fillMaxSize()
    )
//    AndroidView(
//        factory = { context ->
//           WebView(context).apply {
//               webViewClient = object : WebViewClient() {
//                   override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
//                       return false
//                   }
//               }
//               settings.javaScriptEnabled = true
//               loadData(videoId.getFrameVideo(), "text/html", "utf-8")
//           }
//        },
//        update = { view ->
//
//        },
//        modifier = modifier
//    )
}

private fun String.getFrameVideo() =
    """
        <html>
            <body style="padding:0;">
                <iframe 
                    style="width=100%; height=100%; aspect-ratio: 4 / 3; border: none; padding:0; margin:0;"
                    src="https://www.youtube.com/embed/${this}"
                    frameborder="0"
                    allowfullscreen>
                </iframe>
            </body>
        </html>
    """.trimIndent()
