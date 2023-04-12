package com.barabasizsolt.detail.movie.dto

import com.barabasizsolt.api.DataLayerException
import com.barabasizsolt.detail.movie.model.MovieDetail
import com.barabasizsolt.genre.dto.GenreDTO
import com.barabasizsolt.genre.dto.toModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetailDTO(
    @Json(name = "id") val id: Int?,
    @Json(name = "title") val title: String?,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "genres") val genres: List<GenreDTO>?,
    @Json(name = "original_language") val originalLanguage: String?,
    @Json(name = "overview") val overview: String?,
    @Json(name = "release_date") val releaseDate: String?,
    @Json(name = "spoken_languages") val spokenLanguages: List<SpokenLanguagesDTO>?,
    @Json(name = "status") val status: String?,
    @Json(name = "tagline") val tagline: String?,
    @Json(name = "vote_average") val voteAverage: Double?
)

@JsonClass(generateAdapter = true)
data class SpokenLanguagesDTO(
    @Json(name = "iso_639_1") val iso: String?,
    @Json(name = "name") val name: String?
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
        voteAverage = String.format("%.1f", voteAverage)
    )
}