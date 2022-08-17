package com.barabasizsolt.discover.implementation.model.response

import com.barabasizsolt.api.DataLayerException
import com.barabasizsolt.discover.api.model.Movie
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieResponse(
    @Json(name = "id") val id: String?,
    @Json(name = "adult") val adult: Boolean?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "genre_ids") val genreIds: List<String>?,
    @Json(name = "original_language") val originalLanguage: String?,
    @Json(name = "original_title") val originalTitle: String?,
    @Json(name = "overview") val overview: String?,
    @Json(name = "popularity") val popularity: Double?,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "release_date") val releaseDate: String?,
    @Json(name = "vote_average") val voteAverage: Double?
)

//data class MovieResponse(
//    @SerializedName("id") val id: String?,
//    @SerializedName("adult") val adult: Boolean?,
//    @SerializedName("backdrop_path") val backdropPath: String?,
//    @SerializedName("genre_ids") val genreIds: List<String>?,
//    @SerializedName("original_language") val originalLanguage: String?,
//    @SerializedName("original_title") val originalTitle: String?,
//    @SerializedName("overview") val overview: String?,
//    @SerializedName("popularity") val popularity: Double?,
//    @SerializedName("poster_path") val posterPath: String?,
//    @SerializedName("release_date") val releaseDate: String?,
//    @SerializedName("vote_average") val voteAverage: Double?
//)

fun MovieResponse.toModel() : Movie {
    if (
        id == null ||
        adult == null ||
        backdropPath == null ||
        genreIds == null ||
        originalLanguage == null ||
        originalTitle == null ||
        overview == null ||
        popularity == null ||
        posterPath == null ||
        releaseDate == null ||
        voteAverage == null
    ) {
        throw DataLayerException(message = "MovieException: $this")
    }
    return Movie(
        id = id,
        adult = adult,
        backdropPath = backdropPath,
        genreIds = genreIds,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage
    )
}