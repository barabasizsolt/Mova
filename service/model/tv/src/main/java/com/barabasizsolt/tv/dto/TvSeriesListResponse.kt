package com.barabasizsolt.tv.dto

import com.barabasizsolt.api.DataLayerException
import com.barabasizsolt.tv.modell.TvSeriesList
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TvSeriesDiscoverResponse(
    @Json(name = "page") val page: Int?,
    @Json(name = "results") val results: List<TvSeriesResponse>?
)

fun TvSeriesDiscoverResponse.toModel() : TvSeriesList {
    if (page == null || results == null) {
        throw DataLayerException(message = "TvSeriesDiscoverException: $this")
    }
    return TvSeriesList(
        page = page,
        results = results.map { it.toModel() }
    )
}
