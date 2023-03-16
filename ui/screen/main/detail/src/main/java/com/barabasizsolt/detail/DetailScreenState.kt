package com.barabasizsolt.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.barabasizsolt.domain.usecase.screen.detail.GetMovieDetailsUseCase
import com.barabasizsolt.domain.util.result.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@Composable
fun rememberDetailScreenState(
    id: Int,
    scope: CoroutineScope = rememberCoroutineScope(),
    getMovieDetailsUseCase: GetMovieDetailsUseCase = get()
) = remember {
    DetailScreenState(
        id = id,
        scope = scope,
        getMovieDetailsUseCase = getMovieDetailsUseCase
    )
}

class DetailScreenState(
    val id: Int,
    private val scope: CoroutineScope,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) {

    init {
        scope.launch {
            when (val result = getMovieDetailsUseCase(id = id)) {
                is Result.Failure -> {
                    println("<<ERROR: ${result.exception.message}")
                }
                is Result.Success -> {
                    println("<<SUCCESS: ${result.data.id}")
                }
            }
        }
    }
}