package cast_crew.model

data class Crew(
    val id: String,
    val knownForDepartment: String,
    val name: String,
    val profilePath: String?,
    val job: String,
)
