package com.barabasizsolt.tv.dto

import com.barabasizsolt.api.DataLayerException
import com.barabasizsolt.tv.modell.TvSeries
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TvSeriesResponse(
    @Json(name = "id") val id: String?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "genre_ids") val genreIds: List<String>?,
    @Json(name = "original_country") val originalCountry: List<String>?,
    @Json(name = "original_language") val originalLanguage: String?,
    @Json(name = "original_name") val originalTitle: String?,
    @Json(name = "overview") val overview: String?,
    @Json(name = "popularity") val popularity: Double?,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "first_air_date") val firstAirDate: String?,
    @Json(name = "vote_average") val voteAverage: Double?
)

fun TvSeriesResponse.toModel() : TvSeries? {
    if (
        id == null ||
        backdropPath == null ||
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
        voteAverage = voteAverage.toString()
    )
}
