package ui.screen.detail.catalog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.barabasizsolt.mova.domain.model.ContentItem
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.catalog.GradientOverlay
import ui.catalog.MovaFilledButton
import ui.catalog.MovaImage
import ui.catalog.MovaOutlinedButton
import ui.catalog.PeopleCarousel
import ui.catalog.PersonCardSize
import ui.screen.detail.DetailScreenListItem
import ui.theme.AppTheme
import ui.util.ExpandingText
import ui.util.ImageType
import ui.util.getImageKey
import ui.util.withShadow

@Composable
fun ContentHeader(
    item: DetailScreenListItem.HeaderItem,
    onPlayButtonClicked: () -> Unit,
    onAddToFavouriteButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) = Box(modifier = modifier.fillMaxWidth()) {
    ContentGradientImage(posterPath = item.posterPath)
    ContentHeaderInfo(
        item = item,
        onPlayButtonClicked = onPlayButtonClicked,
        onAddToFavouriteButtonClicked = onAddToFavouriteButtonClicked
    )
}

@Composable
private fun BoxScope.ContentGradientImage(posterPath: String?) {
    MovaImage(
        imageUrl = posterPath?.getImageKey(imageType = ImageType.ORIGINAL).orEmpty(),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(ratio = 0.7f)
    )
    GradientOverlay(
        maxHeightFraction = 0.7f,
        colors = listOf(
            Color.Transparent,
            Color.Transparent,
            AppTheme.colors.primary.copy(alpha = 0.8f),
            AppTheme.colors.primary
        )
    )
}

@Composable
private fun BoxScope.ContentHeaderInfo(
    item: DetailScreenListItem.HeaderItem,
    modifier: Modifier = Modifier,
    onPlayButtonClicked: () -> Unit,
    onAddToFavouriteButtonClicked: () -> Unit
) = Column(
    modifier = modifier
        .fillMaxWidth()
        .align(alignment = Alignment.BottomStart),
    verticalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding)
) {
    ContentTitle(title = item.title)
    ContentGenres(genres = item.genres)
    ContentGeneralInfo(item = item)
    Buttons(
        onPlayButtonClicked = onPlayButtonClicked,
        onAddToFavouriteButtonClicked = onAddToFavouriteButtonClicked,
        modifier = Modifier.padding(horizontal = AppTheme.dimens.screenPadding)
    )
    ExpandableContentOverview(overview = item.overview)
    CastCrewContent(
        castCrew = item.castCrew,
        onItemClick = { /*TODO: Implement it*/ }
    )
}

@Composable
private fun ContentTitle(title: String) = Text(
    text = title,
    style = AppTheme.typography.h5.withShadow(),
    fontWeight = FontWeight.Bold,
    color = Color.White,
    modifier = Modifier.padding(horizontal = AppTheme.dimens.screenPadding)
)

@Composable
private fun ContentGeneralInfo(item: DetailScreenListItem.HeaderItem) = LazyRow(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding),
    contentPadding = PaddingValues(horizontal = AppTheme.dimens.screenPadding)
) {
    item { ContentRating(rating = item.voteAverage) }
    item { ContentReleaseDate(releaseDate = item.releaseDate) }
    item { ContentInfoItem(value = item.status) }
    items(items = item.spokenLanguages) { ContentInfoItem(value = it) }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun ContentRating(rating: String) = Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.smallPadding)
) {
    Image(
        painter = painterResource(res = "drawable/ic_rating.xml"),
        contentDescription = null
    )
    Text(
        text = rating,
        style = AppTheme.typography.body2,
        color = AppTheme.colors.onSurface,
        fontWeight = FontWeight.Bold
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun ContentReleaseDate(releaseDate: String) = Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.smallPadding)
) {
    Image(
        painter = painterResource(res = "drawable/ic_arrow.xml"),
        contentDescription = "",
        modifier = Modifier.size(size = 20.dp)
    )
    Text(
        text = releaseDate.split("-")[0],
        style = AppTheme.typography.body2,
        color = AppTheme.colors.onSurface,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun ContentInfoItem(value: String) = Card(
    shape = RoundedCornerShape(size = 10.dp),
    backgroundColor = AppTheme.colors.background,
    border = BorderStroke(
        color = AppTheme.colors.secondary,
        width = 1.dp
    )
) {
    Text(
        text = value,
        style = AppTheme.typography.caption,
        fontWeight = FontWeight.Bold,
        color = AppTheme.colors.secondary,
        modifier = Modifier.padding(all = AppTheme.dimens.contentPadding)
    )
}

@Composable
private fun ContentGenres(genres: List<String>) = LazyRow(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    contentPadding = PaddingValues(horizontal = AppTheme.dimens.screenPadding)
) {
    itemsIndexed(items = genres) { index, genre ->
        Text(
            text = if (index != genres.lastIndex) "$genre • " else genre,
            style = AppTheme.typography.body2.withShadow(),
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            color = Color.White
        )
    }
}

@Composable
private fun Buttons(
    modifier: Modifier = Modifier,
    onPlayButtonClicked: () -> Unit,
    onAddToFavouriteButtonClicked: () -> Unit
) = Row(
    modifier = modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding * 2)
) {
    MovaFilledButton(
        text = "Trailer",
        icon = Icons.Filled.PlayCircle,
        onClick = onPlayButtonClicked,
        modifier = Modifier.weight(weight = 1f)
    )
    MovaOutlinedButton(
        text = "Favourites",
        icon = Icons.Filled.Favorite,
        contentColor = AppTheme.colors.secondary,
        onClick = onAddToFavouriteButtonClicked,
        modifier = Modifier.weight(weight = 1f)
    )
}

@Composable
private fun ExpandableContentOverview(
    overview: String,
    modifier: Modifier = Modifier
) = ExpandingText(
    text = overview,
    color = AppTheme.colors.onSurface,
    tailTextColor = AppTheme.colors.secondary,
    style = AppTheme.typography.body2,
    modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = AppTheme.dimens.screenPadding)
)

@Composable
fun CastCrewContent(
    castCrew: List<ContentItem.Person>,
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit
) = PeopleCarousel(
    modifier = modifier.fillMaxWidth(),
    showDivider = false,
    personCardSize = PersonCardSize.MEDIUM,
    items = castCrew,
    onItemClick = onItemClick,
    onHeaderClick = {}
)
