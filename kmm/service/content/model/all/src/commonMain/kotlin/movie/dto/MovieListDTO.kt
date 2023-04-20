package movie.dto

import DataLayerException
import movie.model.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieListDTO(
    @SerialName(value = "page") val page: Int?,
    @SerialName(value = "results") val results: List<MovieDTO>?
)

fun MovieListDTO.toModel() : List<Movie> {
    if (page == null || results == null) {
        throw DataLayerException(message = "MovieListException: $this")
    }
    return results.mapNotNull { it.toModel() }
}
