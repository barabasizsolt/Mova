package tv.dto

import tv.modell.TvSeries
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvSeriesDTO(
    @SerialName(value = "id") val id: String?,
    @SerialName(value = "backdrop_path") val backdropPath: String?,
    @SerialName(value = "genre_ids") val genreIds: List<String>?,
    @SerialName(value = "origin_country") val originalCountry: List<String>?,
    @SerialName(value = "original_language") val originalLanguage: String?,
    @SerialName(value = "original_name") val originalTitle: String?,
    @SerialName(value = "overview") val overview: String?,
    @SerialName(value = "popularity") val popularity: Double?,
    @SerialName(value = "poster_path") val posterPath: String?,
    @SerialName(value = "first_air_date") val firstAirDate: String?,
    @SerialName(value = "vote_average") val voteAverage: Double?
)

fun TvSeriesDTO.toModel() : TvSeries? {
    if (
        id == null ||
        genreIds == null ||
        originalCountry == null ||
        originalLanguage == null ||
        originalTitle == null ||
        overview == null ||
        popularity == null ||
        firstAirDate == null ||
        voteAverage == null
    ) {
        return null
    }
    return TvSeries(
        id = id,
        backdropPath = backdropPath,
        genreIds = genreIds,
        originalCountry = originalCountry,
        originalLanguage = originalLanguage,
        originalName = originalTitle,
        overview = overview,
        popularity = popularity.toString(),
        posterPath = posterPath,
        firstAirDate = firstAirDate,
        voteAverage = voteAverage.toString().take(n = 3)
        /*TODO: Fix this*/ // String.format("%.1f", voteAverage)
    )
}
