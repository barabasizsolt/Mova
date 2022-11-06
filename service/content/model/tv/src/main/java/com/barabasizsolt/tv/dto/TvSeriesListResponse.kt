package com.barabasizsolt.tv.dto

import com.barabasizsolt.api.DataLayerException
import com.barabasizsolt.tv.modell.TvSeries
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TvSeriesDiscoverResponse(
    @Json(name = "page") val page: Int?,
    @Json(name = "results") val results: List<TvSeriesResponse>?
)

fun TvSeriesDiscoverResponse.toModel() : List<TvSeries> {
    if (page == null || results == null) {
        throw DataLayerException(message = "TvSeriesDiscoverException: $this")
    }
    return results.mapNotNull { it.toModel() }
}
