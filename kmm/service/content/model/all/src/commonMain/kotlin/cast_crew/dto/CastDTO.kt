package cast_crew.dto

import cast_crew.model.Cast
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastDTO(
    @SerialName(value = "id") val id: Int?,
    @SerialName(value = "name") val name: String?,
    @SerialName(value = "known_for_department") val knownForDepartment: String?,
    @SerialName(value = "profile_path") val profilePath: String?
)

fun CastDTO.toModel() : Cast? {
    if (
        id == null ||
        name == null ||
        knownForDepartment == null
    ) {
        return null
    }
    return Cast(
        id = id.toString(),
        name = name,
        knownForDepartment = knownForDepartment,
        profilePath = profilePath
    )
}