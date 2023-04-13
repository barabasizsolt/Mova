package com.barabasizsolt.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.barabasizsolt.catalog.WatchableWithRating
import com.barabasizsolt.detail.catalog.ContentHeader
import com.barabasizsolt.detail.catalog.ContentTabs
import com.barabasizsolt.detail.catalog.Review
import com.barabasizsolt.domain.model.ContentItem
import com.barabasizsolt.domain.model.toContentItem
import com.barabasizsolt.mova.ui.screen.main.detail.R
import com.barabasizsolt.theme.AppTheme
import com.barabasizsolt.util.ListItemDiff
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.DefaultPlayerUiController
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


enum class ItemViewType {
    HEADER, TAB, SIMILAR_MOVIE, VIDEO, REVIEW, EMPTY
}

@Composable
fun rememberDetailScreenAdapter(
    onPlayButtonClicked: () -> Unit,
    onAddToFavouriteButtonClicked: () -> Unit,
    onTabIndexChange: (Int) -> Unit,
    onMovieClicked: (Int) -> Unit
) = remember {
    DetailScreenAdapter(
        onPlayButtonClicked = onPlayButtonClicked,
        onAddToFavouriteButtonClicked = onAddToFavouriteButtonClicked,
        onTabIndexChange = onTabIndexChange,
        onMovieClicked = onMovieClicked
    )
}

class DetailScreenAdapter(
    private val onPlayButtonClicked: () -> Unit,
    private val onAddToFavouriteButtonClicked: () -> Unit,
    private val onTabIndexChange: (Int) -> Unit,
    private val onMovieClicked: (Int) -> Unit
) : ListAdapter<DetailScreenListItem, RecyclerView.ViewHolder>(ListItemDiff()) {

    override fun getItemViewType(position: Int): Int = when(getItem(position)) {
        is DetailScreenListItem.HeaderItem -> ItemViewType.HEADER.ordinal
        is DetailScreenListItem.TabsItem -> ItemViewType.TAB.ordinal
        is DetailScreenListItem.SimilarMovieItem -> ItemViewType.SIMILAR_MOVIE.ordinal
        is DetailScreenListItem.VideoItem -> ItemViewType.VIDEO.ordinal
        is DetailScreenListItem.ReviewItem -> ItemViewType.REVIEW.ordinal
        is DetailScreenListItem.EmptyItem -> ItemViewType.EMPTY.ordinal
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) = when (viewType) {
        ItemViewType.HEADER.ordinal -> HeaderViewHolder.create(
            composeView = ComposeView(viewGroup.context),
            onPlayButtonClicked = onPlayButtonClicked,
            onAddToFavouriteButtonClicked = onAddToFavouriteButtonClicked
        )
        ItemViewType.TAB.ordinal -> TabsViewHolder.create(
            composeView = ComposeView(viewGroup.context),
            onTabIndexChange = onTabIndexChange
        )
        ItemViewType.SIMILAR_MOVIE.ordinal -> SimilarMovieViewHolder.create(
            composeView = ComposeView(viewGroup.context),
            onMovieClicked = onMovieClicked
        )
        ItemViewType.VIDEO.ordinal -> VideoViewHolder.create(
            parent = viewGroup
        )
        ItemViewType.REVIEW.ordinal -> ReviewViewHolder.create(
            composeView = ComposeView(viewGroup.context)
        )
        ItemViewType.EMPTY.ordinal -> EmptyViewHolder.create(
            composeView = ComposeView(viewGroup.context)
        )
        else -> throw IllegalStateException("Invalid view type: $viewType.")
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) = when (val listItem = getItem(position)) {
        is DetailScreenListItem.HeaderItem -> (viewHolder as HeaderViewHolder).bind(listItem = listItem)
        is DetailScreenListItem.TabsItem -> (viewHolder as TabsViewHolder).bind(listItem = listItem)
        is DetailScreenListItem.SimilarMovieItem -> (viewHolder as SimilarMovieViewHolder).bind(listItem = listItem)
        is DetailScreenListItem.VideoItem -> (viewHolder as VideoViewHolder).bind(listItem = listItem)
        is DetailScreenListItem.ReviewItem -> (viewHolder as ReviewViewHolder).bind(listItem = listItem)
        is DetailScreenListItem.EmptyItem -> (viewHolder as EmptyViewHolder).bind()
    }

    class HeaderViewHolder private constructor(
        private val composeView: ComposeView,
        private val onPlayButtonClicked: () -> Unit,
        private val onAddToFavouriteButtonClicked: () -> Unit
    ) : RecyclerView.ViewHolder(composeView) {

        fun bind(listItem: DetailScreenListItem.HeaderItem) {
            composeView.setContent {
                ContentHeader(
                    item = listItem,
                    onPlayButtonClicked = onPlayButtonClicked,
                    onAddToFavouriteButtonClicked = onAddToFavouriteButtonClicked,
                    modifier = Modifier.padding(bottom = AppTheme.dimens.contentPadding)
                )
            }
        }

        companion object {
            fun create(
                composeView: ComposeView,
                onPlayButtonClicked: () -> Unit,
                onAddToFavouriteButtonClicked: () -> Unit
            ) = HeaderViewHolder(
                composeView = composeView,
                onPlayButtonClicked = onPlayButtonClicked,
                onAddToFavouriteButtonClicked = onAddToFavouriteButtonClicked
            )
        }
    }

    class TabsViewHolder private constructor(
        private val composeView: ComposeView,
        private val onTabIndexChange: (Int) -> Unit
    ) : RecyclerView.ViewHolder(composeView) {

        fun bind(listItem: DetailScreenListItem.TabsItem) {
            composeView.setContent {
                ContentTabs(
                    tabs = listItem.tabs,
                    onTabIndexChange = onTabIndexChange,
                    modifier = Modifier.padding(bottom = AppTheme.dimens.contentPadding)
                )
            }
        }

        companion object {
            fun create(
                composeView: ComposeView,
                onTabIndexChange: (Int) -> Unit
            ) = TabsViewHolder(
                composeView = composeView,
                onTabIndexChange = onTabIndexChange
            )
        }
    }

    class SimilarMovieViewHolder private constructor(
        private val composeView: ComposeView,
        private val onMovieClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(composeView) {

        fun bind(listItem: DetailScreenListItem.SimilarMovieItem) {
            composeView.setContent {
                WatchableWithRating(
                    item = listItem.movie.toContentItem() as ContentItem.Watchable,
                    onClick = onMovieClicked,
                    modifier = Modifier.padding(
                        start = if (listItem.movieIndex % 2 == 0) AppTheme.dimens.screenPadding else AppTheme.dimens.smallPadding,
                        end = if (listItem.movieIndex % 2 == 0) AppTheme.dimens.smallPadding else AppTheme.dimens.screenPadding,
                        top = AppTheme.dimens.contentPadding,
                        bottom = if (listItem.isLast) AppTheme.dimens.screenPadding else 0.dp
                    )
                )
            }
        }

        companion object {
            fun create(
                composeView: ComposeView,
                onMovieClicked: (Int) -> Unit
            ) = SimilarMovieViewHolder(
                composeView = composeView,
                onMovieClicked = onMovieClicked
            )
        }
    }

    class VideoViewHolder private constructor(
        private val view: View
    ) : RecyclerView.ViewHolder(view) {

        private val textView: TextView = view.findViewById(R.id.title)
        val youTubePlayerView: YouTubePlayerView = view.findViewById(R.id.youtube_player_view)

        /*TODO:
           - register lifecycle observer
           - find a way to apply Compose Theme's to XML
           - Implement to play it in full screen
        */
        fun bind(listItem: DetailScreenListItem.VideoItem) {
            textView.text = listItem.video.name
            youTubePlayerView.addYouTubePlayerListener(
                youTubePlayerListener = object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.cueVideo(videoId = listItem.video.videoId, startSeconds = 0f)
                    }
                }
            )
        }

        companion object {
            fun create(parent: ViewGroup) = VideoViewHolder(
                view = LayoutInflater.from(parent.context).inflate(R.layout.layout_movie_video, parent, false)
            ).also {
                val listener =  object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        val defaultPlayerUiController = DefaultPlayerUiController(it.youTubePlayerView, youTubePlayer)
                        it.youTubePlayerView.setCustomPlayerUi(defaultPlayerUiController.rootView)
                    }
                }
                val options: IFramePlayerOptions = IFramePlayerOptions.Builder().controls(0).build()
                it.youTubePlayerView.enableAutomaticInitialization = false
                it.youTubePlayerView.initialize(listener, options)
            }
        }
    }

    class ReviewViewHolder private constructor(
        private val composeView: ComposeView
    ) : RecyclerView.ViewHolder(composeView) {

        fun bind(listItem: DetailScreenListItem.ReviewItem) {
            composeView.setContent {
                Review(
                    review = listItem.review,
                    modifier = Modifier.padding(
                        top = AppTheme.dimens.contentPadding,
                        bottom = if (listItem.isLast) AppTheme.dimens.screenPadding else AppTheme.dimens.smallPadding
                    )
                )
            }
        }

        companion object {
            fun create(composeView: ComposeView) = ReviewViewHolder(composeView = composeView)
        }
    }

    class EmptyViewHolder private constructor(
        private val composeView: ComposeView
    ) : RecyclerView.ViewHolder(composeView) {

        fun bind() {
            composeView.setContent {
                EmptyTabItem()
            }
        }

        companion object {
            fun create(composeView: ComposeView) = EmptyViewHolder(composeView = composeView)
        }
    }
}