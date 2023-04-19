package genre.dto

import genre.model.Genre
import com.barabasizsolt.network.api.DataLayerException
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreListDTO(
    @SerialName(value = "genres") val genres: List<GenreDTO>?
)

fun GenreListDTO.toModel() : List<Genre> {
    if (genres == null) {
        throw DataLayerException(message = "GenreListException: $this")
    }
    return genres.mapNotNull { it.toModel() }
}
