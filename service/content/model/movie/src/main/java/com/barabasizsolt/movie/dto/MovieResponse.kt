package com.barabasizsolt.movie.dto

import com.barabasizsolt.api.DataLayerException
import com.barabasizsolt.movie.model.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieResponse(
    @Json(name = "id") val id: String?,
    @Json(name = "adult") val adult: Boolean?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "genre_ids") val genreIds: List<Int>?,
    @Json(name = "original_language") val originalLanguage: String?,
    @Json(name = "original_title") val originalTitle: String?,
    @Json(name = "title") val title: String?,
    @Json(name = "overview") val overview: String?,
    @Json(name = "popularity") val popularity: Double?,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "release_date") val releaseDate: String?,
    @Json(name = "vote_average") val voteAverage: Double?
)

fun MovieResponse.toModel() : Movie? {
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
        id = id,
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
        voteAverage = voteAverage.toString()
    )
}