package com.barabasizsolt.movie.implementation

import com.barabasizsolt.movie.api.MovieService
import com.barabasizsolt.movie.model.Movie
import com.barabasizsolt.util.RefreshType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class MovieServiceImpl(private val remoteSource: MovieRemoteSource) : MovieService {

    private val _popularMovies = MutableStateFlow<List<Movie>>(value = emptyList())
    override val popularMovies: Flow<List<Movie>> = _popularMovies
    private var popularMoviePage = 1

    private val _upcomingMovies = MutableStateFlow<List<Movie>>(value = emptyList())
    override val upcomingMovies: Flow<List<Movie>> = _upcomingMovies

    private val _topRatedMovies = MutableStateFlow<List<Movie>>(value = emptyList())
    override val topRatedMovies: Flow<List<Movie>> = _topRatedMovies

    private val _nowPlayingMovies = MutableStateFlow<List<Movie>>(value = emptyList())
    override val nowPlayingMovies: Flow<List<Movie>> = _nowPlayingMovies

    override suspend fun getPopularMovies(refreshType: RefreshType): List<Movie> = when (refreshType) {
        RefreshType.CACHE_IF_POSSIBLE -> {
            _popularMovies.value.ifEmpty {
                remoteSource.getPopularMovies(page = 1).also { _popularMovies.value = it }
            }
        }
        RefreshType.NEXT_PAGE -> {
            val moviesOnCurrentPage = remoteSource.getPopularMovies(page = popularMoviePage++)
            _popularMovies.value = _popularMovies.value.plus(moviesOnCurrentPage)
            println("Size: ${_popularMovies.value.size}")
            _popularMovies.value
        }
        RefreshType.FORCE_REFRESH -> {
            remoteSource.getPopularMovies(page = 1).also {
                _popularMovies.value = it
                popularMoviePage = 1
            }
        }
    }

    override suspend fun getUpcomingMovies(refreshType: RefreshType): List<Movie> = _upcomingMovies.value.ifEmpty {
        remoteSource.getUpcomingMovies(page = 1).also { _upcomingMovies.value = it }
    }

    override suspend fun getTopRatedMovies(refreshType: RefreshType): List<Movie> = _topRatedMovies.value.ifEmpty {
        remoteSource.getTopRatedMovies(page = 1).also { _topRatedMovies.value = it }
    }

    override suspend fun getNowPlayingMovies(refreshType: RefreshType): List<Movie> = _nowPlayingMovies.value.ifEmpty {
        remoteSource.getNowPlayingMovies(page = 1).also { _nowPlayingMovies.value = it }
    }

    override fun clearPopularMovies() {
        _popularMovies.value = emptyList()
    }

    override fun clearUpcomingMovies() {
        _upcomingMovies.value = emptyList()
    }

    override fun clearTopRatedMovies() {
        _topRatedMovies.value = emptyList()
    }

    override fun clearNowPlayingMovies() {
        _nowPlayingMovies.value = emptyList()
    }
}