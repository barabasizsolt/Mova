package ui.catalog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.barabasizsolt.mova.domain.model.ContentItem
import ui.theme.AppTheme
import ui.util.ImageType
import ui.util.getImageKey
import ui.util.withShadow

enum class PersonCardSize(val size: Dp) {
    SMALL(size = 45.dp),
    MEDIUM(size = 50.dp),
    LARGE(size = 65.dp)
}

@Composable
internal fun MediumPersonCard(
    modifier: Modifier = Modifier,
    item: ContentItem.Person,
    aspectRatio: Float = 0.7f,
    onClick: () -> Unit
) = Box {
    MovaImage(
        imageUrl = item.posterPath.getImageKey(imageType = ImageType.ORIGINAL),
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(shape = AppTheme.shapes.medium)
            .aspectRatio(ratio = aspectRatio)
            .clickable { onClick() }
    )
    Text(
        text = item.name,
        style = AppTheme.typography.body2.withShadow(),
        color = Color.White,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .align(alignment = Alignment.BottomCenter)
            .padding(all = AppTheme.dimens.contentPadding + AppTheme.dimens.smallPadding)
    )
}

@Composable
internal fun PersonCard(
    modifier: Modifier = Modifier,
    person: ContentItem.Person,
    personCardSize: PersonCardSize,
    onClick: () -> Unit
) = Row(
    modifier = modifier.clickable { onClick() },
    horizontalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.contentPadding),
    verticalAlignment = Alignment.CenterVertically
) {
    val names = person.name.split(" ", limit = 2)

    MovaImage(
        imageUrl = person.posterPath.getImageKey(imageType = ImageType.ORIGINAL),
        modifier = Modifier
            .size(size = personCardSize.size)
            .clip(shape = CircleShape),
        contentScale = ContentScale.Crop
    )
    Column {
        NameText(name = names[0], personCardSize = personCardSize)
        if (names.size > 1) {
            NameText(name = names[1], personCardSize = personCardSize)
        }
        when {
            person.knownForDepartment != null && person.job != null -> DetailText(
                detail = "${person.knownForDepartment}/${person.job}"
            )
            person.knownForDepartment != null -> DetailText(
                detail = person.knownForDepartment.orEmpty()
            )
        }
    }
}

@Composable
private fun NameText(
    modifier: Modifier = Modifier,
    name: String,
    personCardSize: PersonCardSize,
) = Text(
    modifier = modifier,
    text = name,
    maxLines = 1,
    overflow = TextOverflow.Ellipsis,
    style = when (personCardSize) {
        PersonCardSize.LARGE -> AppTheme.typography.body2
        PersonCardSize.MEDIUM, PersonCardSize.SMALL -> AppTheme.typography.caption
    },
    fontWeight = FontWeight.Bold,
    color = AppTheme.colors.onPrimary
)

@Composable
private fun DetailText(
    modifier: Modifier = Modifier,
    detail: String
) = Text(
    modifier = modifier,
    text = detail,
    maxLines = 1,
    overflow = TextOverflow.Ellipsis,
    style = AppTheme.typography.caption,
    color = Color.Gray
)