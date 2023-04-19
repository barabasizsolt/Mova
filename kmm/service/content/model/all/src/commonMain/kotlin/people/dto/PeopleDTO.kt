package people.dto

import people.model.People
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PeopleDTO(
    @SerialName(value = "id") val id: String?,
    @SerialName(value = "adult") val adult: Boolean?,
    @SerialName(value = "profile_path") val profilePath: String?,
    @SerialName(value = "name") val name: String?
)

fun PeopleDTO.toModel() : People? {
    if (id == null || adult == null || profilePath == null || name == null){
        return null
    }
    return People(id = id, adult = adult, profilePath = profilePath, name = name)
}