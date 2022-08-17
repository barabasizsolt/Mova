package com.barabasizsolt.discover.implementation.model.response

import com.barabasizsolt.api.DataLayerException
import com.barabasizsolt.discover.api.model.TvSeriesDiscover
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TvSeriesDiscoverResponse(
    @Json(name = "page") val page: Int?,
    @Json(name = "results") val results: List<TvSeriesResponse>?
)

fun TvSeriesDiscoverResponse.toModel() : TvSeriesDiscover {
    if (page == null || results == null) {
        throw DataLayerException(message = "TvSeriesDiscoverException: $this")
    }
    return TvSeriesDiscover(
        page = page,
        results = results.map { it.toModel() }
    )
}
