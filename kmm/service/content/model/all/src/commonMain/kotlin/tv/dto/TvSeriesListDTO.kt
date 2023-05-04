package tv.dto

import DataLayerException
import tv.modell.TvSeries
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvSeriesDiscoverDTO(
    @SerialName(value = "page") val page: Int?,
    @SerialName(value = "results") val results: List<TvSeriesDTO>?
)

fun TvSeriesDiscoverDTO.toModel() : List<TvSeries> {
    if (page == null || results == null) {
        throw DataLayerException(message = "TvSeriesDiscoverException: $this")
    }
    return results.mapNotNull { it.toModel() }
}
