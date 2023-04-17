package com.barabasizsolt.mova.ui.catalog

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.DefaultPlayerUiController
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

/*TODO: [mid] find a way to keep the loaded video state*/
@Composable
fun YoutubePlayer(
    videoId: String,
    lifecycleOwner: LifecycleOwner,
    modifier: Modifier = Modifier
) = AndroidView(
    factory = { context ->
        val view = YouTubePlayerView(context)
        val options: IFramePlayerOptions = IFramePlayerOptions.Builder().controls(0).build()
        val playerListener: AbstractYouTubePlayerListener = object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                view.setCustomPlayerUi(DefaultPlayerUiController(view, youTubePlayer).rootView)
                youTubePlayer.cueVideo(videoId, 0F)
            }
        }
        lifecycleOwner.lifecycle.addObserver(view)
        view.enableAutomaticInitialization = false
        view.initialize(playerListener, options)
        view
    },
    modifier = modifier.aspectRatio(ratio = 1.4f)
)
