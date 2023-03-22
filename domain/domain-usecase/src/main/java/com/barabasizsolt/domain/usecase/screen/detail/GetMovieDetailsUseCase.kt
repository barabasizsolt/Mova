package com.barabasizsolt.domain.usecase.screen.detail

import com.barabasizsolt.category.Category
import com.barabasizsolt.domain.model.DetailScreenContent
import com.barabasizsolt.domain.model.toContentItem
import com.barabasizsolt.domain.usecase.helper.`cast-crew`.GetCastCrewUseCase
import com.barabasizsolt.domain.usecase.helper.detail.GetMovieDetailUseCase
import com.barabasizsolt.domain.usecase.helper.movie.similar.GetSimilarMoviesUseCase
import com.barabasizsolt.domain.usecase.helper.review.GetReviewsUseCase
import com.barabasizsolt.domain.usecase.helper.video.GetVideosUseCase
import com.barabasizsolt.domain.util.result.Result
import com.barabasizsolt.movie.model.Movie
import com.barabasizsolt.pagination.api.RefreshType
import com.barabasizsolt.review.model.Review
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

/*TODO: Improve It*/
class GetMovieDetailsUseCase(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getVideosUseCase: GetVideosUseCase,
    private val  getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    private val getReviewsUseCase: GetReviewsUseCase,
    private val getCastCrewUseCase: GetCastCrewUseCase
) {

    suspend operator fun invoke(id: Int): Result<DetailScreenContent.MovieDetails> = coroutineScope {
        val movieDetails = async { getMovieDetailUseCase(id = id) }
        val videos = async { getVideosUseCase(id = id, category = Category.MOVIE) }
        val similarMovies = async { getSimilarMoviesUseCase(id = id, refreshType = RefreshType.FORCE_REFRESH) }
        val reviews = async { getReviewsUseCase(id = id, category = Category.MOVIE, refreshType = RefreshType.FORCE_REFRESH) }
        val castCrew = async { getCastCrewUseCase(id = id, category = Category.MOVIE) }

        val movieDetailsResult = movieDetails.await()
        if (movieDetailsResult is Result.Failure) return@coroutineScope Result.Failure(exception = movieDetailsResult.exception)

        val videosResult = videos.await()
        if (videosResult is Result.Failure) return@coroutineScope Result.Failure(exception = videosResult.exception)

        val similarMoviesResult = similarMovies.await()
        if (similarMoviesResult is Result.Failure) return@coroutineScope Result.Failure(exception = similarMoviesResult.exception)

        val reviewsResult = reviews.await()
        if (reviewsResult is Result.Failure) return@coroutineScope Result.Failure(exception = reviewsResult.exception)

        val castCrewResult = castCrew.await()
        if (castCrewResult is Result.Failure) return@coroutineScope Result.Failure(exception = castCrewResult.exception)

        val movieDetailsResultData = (movieDetailsResult as Result.Success).data

        return@coroutineScope Result.Success(
            data = DetailScreenContent.MovieDetails(
                id = movieDetailsResultData.id,
                title = movieDetailsResultData.title,
                posterPath = movieDetailsResultData.posterPath,
                backdropPath = movieDetailsResultData.backdropPath,
                genres = movieDetailsResultData.genres.values.toList(),
                originalLanguage = movieDetailsResultData.originalLanguage,
                overview = movieDetailsResultData.overview,
                releaseDate = movieDetailsResultData.releaseDate,
                spokenLanguages = movieDetailsResultData.spokenLanguages.filter { it.isNotEmpty() },
                status = movieDetailsResultData.status,
                tagline = movieDetailsResultData.tagline,
                voteAverage = movieDetailsResultData.voteAverage,
                videos = (videosResult as Result.Success).data,
                similarMovies = (similarMoviesResult as Result.Success).data.map { it as Movie },
                reviews = (reviewsResult as Result.Success).data.map { it as Review },
                castCrew = buildList {
                    val data = (castCrewResult as Result.Success).data
                    val casts = data.casts.filter { it.profilePath != null }.map { it.toContentItem() }
                    val crews = data.crews.filter { it.profilePath != null }.map { it.toContentItem() }
                    addAll((casts + crews).distinctBy { it.id })
                }
            )
        )
    }
}