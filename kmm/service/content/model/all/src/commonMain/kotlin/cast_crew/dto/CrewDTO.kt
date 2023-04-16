package cast_crew.dto

import cast_crew.model.Crew
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CrewDTO(
    @SerialName(value = "id") val id: Int?,
    @SerialName(value = "name") val name: String?,
    @SerialName(value = "known_for_department") val knownForDepartment: String?,
    @SerialName(value = "profile_path") val profilePath: String?,
    @SerialName(value = "job") val job: String?
)

fun CrewDTO.toModel() : Crew? {
    if (
        id == null ||
        name == null ||
        knownForDepartment == null ||
        job == null
    ) {
        return null
    }
    return Crew(
        id = id.toString(),
        name = name,
        knownForDepartment = knownForDepartment,
        profilePath = profilePath,
        job = job
    )
}