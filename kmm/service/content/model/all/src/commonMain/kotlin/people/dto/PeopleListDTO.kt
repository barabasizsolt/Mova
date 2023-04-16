package people.dto

import com.barabasizsolt.network.api.DataLayerException
import people.model.People
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PeopleListDTO(
    @SerialName(value = "page") val page: Int?,
    @SerialName(value = "results") val results: List<PeopleDTO>?
)

fun PeopleListDTO.toModel() : List<People> {
    if (page == null || results == null) {
        throw DataLayerException(message = "PeopleListException: $this")
    }
    return results.mapNotNull { it.toModel() }
}