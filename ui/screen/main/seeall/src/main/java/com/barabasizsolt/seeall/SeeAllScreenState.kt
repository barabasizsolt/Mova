package com.barabasizsolt.seeall

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun rememberSeeAllScreenState(
    contentType: String
) = remember {
    SeeAllScreenState(
        contentType = contentType
    )
}

class SeeAllScreenState(
    val contentType: String
) {

}