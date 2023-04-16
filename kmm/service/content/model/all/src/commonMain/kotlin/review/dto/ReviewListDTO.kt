package review.dto

import com.barabasizsolt.network.api.DataLayerException
import review.model.Review
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewListDTO(
    @SerialName(value = "page") val page: Int?,
    @SerialName(value = "results") val results: List<ReviewDTO>?
)

fun ReviewListDTO.toModel() : List<Review> {
    if (page == null || results == null) {
        throw DataLayerException(message = "ReviewListException: $this")
    }
    return results.mapNotNull { it.toModel() }
}