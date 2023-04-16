package genre.dto

import genre.model.Genre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreDTO(
    @SerialName(value = "id") val id: Long?,
    @SerialName(value = "name") val name: String?
)

fun GenreDTO.toModel() : Genre? {
    if (id == null || name == null) return null
    return Genre(id = id, name = name)
}
