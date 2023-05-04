package detail.movie.model

data class MovieDetail(
    val id: String,
    val title: String,
    val posterPath: String?,
    val backdropPath: String?,
    val genres: Map<Long, String>,
    val originalLanguage: String,
    val overview: String,
    val releaseDate: String,
    val spokenLanguages: List<String>,
    val status: String,
    val tagline: String,
    val voteAverage: String
)