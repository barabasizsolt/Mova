package detail.movie.dto

import detail.movie.model.MovieDetail
import com.barabasizsolt.network.api.DataLayerException
import genre.dto.GenreDTO
import genre.dto.toModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailDTO(
    @SerialName(value = "id") val id: Int?,
    @SerialName(value = "title") val title: String?,
    @SerialName(value = "poster_path") val posterPath: String?,
    @SerialName(value = "backdrop_path") val backdropPath: String?,
    @SerialName(value = "genres") val genres: List<GenreDTO>?,
    @SerialName(value = "original_language") val originalLanguage: String?,
    @SerialName(value = "overview") val overview: String?,
    @SerialName(value = "release_date") val releaseDate: String?,
    @SerialName(value = "spoken_languages") val spokenLanguages: List<SpokenLanguagesDTO>?,
    @SerialName(value = "status") val status: String?,
    @SerialName(value = "tagline") val tagline: String?,
    @SerialName(value = "vote_average") val voteAverage: Double?
)

@Serializable
data class SpokenLanguagesDTO(
    @SerialName(value = "iso_639_1") val iso: String?,
    @SerialName(value = "name") val name: String?
)

fun MovieDetailDTO.toModel(): MovieDetail {
    if (
        id == null ||
        title == null ||
        genres == null ||
        originalLanguage == null ||
        overview == null ||
        releaseDate == null ||
        spokenLanguages == null ||
        status == null ||
        tagline == null ||
        voteAverage == null
    ) {
        throw DataLayerException("MovieDetailsException: $this")
    }
    return MovieDetail(
        id = id.toString(),
        title = title,
        posterPath = posterPath,
        backdropPath = backdropPath,
        genres = genres.mapNotNull { it.toModel() }.associate { it.id to it.name },
        originalLanguage = originalLanguage,
        overview = overview,
        releaseDate = releaseDate,
        spokenLanguages = spokenLanguages.map { it.name.orEmpty() },
        status = status,
        tagline = tagline,
        voteAverage = voteAverage.toString().take(n = 3)
            /*TODO: Fix this*/ // String.format("%.1f", voteAverage)
    )
}