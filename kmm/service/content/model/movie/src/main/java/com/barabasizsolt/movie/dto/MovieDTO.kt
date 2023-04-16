package com.barabasizsolt.movie.dto

import com.barabasizsolt.movie.model.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDTO(
    @SerialName(value = "id") val id: Int?,
    @SerialName(value = "adult") val adult: Boolean?,
    @SerialName(value = "backdrop_path") val backdropPath: String?,
    @SerialName(value = "genre_ids") val genreIds: List<Int>?,
    @SerialName(value = "original_language") val originalLanguage: String?,
    @SerialName(value = "original_title") val originalTitle: String?,
    @SerialName(value = "title") val title: String?,
    @SerialName(value = "overview") val overview: String?,
    @SerialName(value = "popularity") val popularity: Double?,
    @SerialName(value = "poster_path") val posterPath: String?,
    @SerialName(value = "release_date") val releaseDate: String?,
    @SerialName(value = "vote_average") val voteAverage: Double?,
    @SerialName(value = "vote_count") val voteCount: Int?,
    @SerialName(value = "video") val video: Boolean?,
)

fun MovieDTO.toModel() : Movie? {
    if (
        id == null ||
        adult == null ||
        backdropPath == null ||
        genreIds == null ||
        originalLanguage == null ||
        originalTitle == null ||
        title == null ||
        overview == null ||
        popularity == null ||
        releaseDate == null ||
        voteAverage == null
    ) {
        return null
    }
    return Movie(
        id = id.toString(),
        adult = adult,
        backdropPath = backdropPath,
        genreIds = genreIds,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        title = title,
        overview = overview,
        popularity = popularity.toString(),
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteAverage = String.format("%.1f", voteAverage)
    )
}