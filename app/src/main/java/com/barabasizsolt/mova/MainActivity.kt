package com.barabasizsolt.mova

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.barabasizsolt.domain.useCase.discover.movie.GetMovieDiscoverUseCase
import com.barabasizsolt.domain.util.Result
import com.barabasizsolt.mova.ui.theme.MovaTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovaTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
                val useCase by inject<GetMovieDiscoverUseCase>()
                LaunchedEffect(
                    key1 = Unit,
                    block = {
                        this.launch {
                            when(val result = useCase()) {
                                is Result.Failure -> {
                                    println("Error: ${result.exception}")
                                }
                                is Result.Success -> {
                                    println("Success: ${result.data}")
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MovaTheme {
        Greeting("Android")
    }
}