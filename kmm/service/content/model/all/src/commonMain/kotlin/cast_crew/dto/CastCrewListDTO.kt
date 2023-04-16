package cast_crew.dto

import cast_crew.model.CastCrew
import com.barabasizsolt.network.api.DataLayerException
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastCrewListDTO(
    @SerialName(value = "id") val id: Int?,
    @SerialName(value = "cast") val cast: List<CastDTO>?,
    @SerialName(value = "crew") val crew: List<CrewDTO>?,
)

fun CastCrewListDTO.toModel() : CastCrew {
    if (id == null || cast == null || crew == null) {
        throw DataLayerException(message = "CastCrewListException: $this")
    }
    return CastCrew(
        casts = cast.mapNotNull { it.toModel() },
        crews = crew.mapNotNull { it.toModel() }
    )
}