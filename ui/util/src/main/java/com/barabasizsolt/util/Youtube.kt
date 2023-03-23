package com.barabasizsolt.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController

@Composable
fun YoutubePlayer(
    videoId: String,
    lifecycleOwner: LifecycleOwner,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { context ->
            val view = YouTubePlayerView(context)
            val options: IFramePlayerOptions = IFramePlayerOptions.Builder().controls(0).build()

            val listener = object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    view.setCustomPlayerUi(DefaultPlayerUiController(view, youTubePlayer).rootView)
                    youTubePlayer.cueVideo(videoId, 0f)
                }
            }

            view.enableAutomaticInitialization = false
            view.initialize(
                youTubePlayerListener = listener,
                playerOptions = options
            )

            lifecycleOwner.lifecycle.addObserver(view)

            view
        },
        update = { view ->
            /*TODO: not working*/
            view.addFullScreenListener(
                fullScreenListener = object : YouTubePlayerFullScreenListener {
                    override fun onYouTubePlayerEnterFullScreen() {
                        view.enterFullScreen()
                    }

                    override fun onYouTubePlayerExitFullScreen() {
                        view.exitFullScreen()
                    }
                }
            )
        },
        modifier = modifier
    )
}