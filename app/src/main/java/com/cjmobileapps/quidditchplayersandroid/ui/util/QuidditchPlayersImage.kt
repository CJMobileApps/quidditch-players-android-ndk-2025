package com.cjmobileapps.quidditchplayersandroid.ui.util

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun QuidditchPlayersImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentDescription: String,
    contentScale: ContentScale = ContentScale.Fit
) {
    val showImageShimmer = remember { mutableStateOf(true) }

    AsyncImage(
        modifier = modifier
            .background(shimmerBrush(showShimmer = showImageShimmer.value)),
        model = imageUrl,
        contentScale = contentScale,
        contentDescription = contentDescription,
        onLoading = { showImageShimmer.value = true },
        onSuccess = { showImageShimmer.value = false },
        onError = { showImageShimmer.value = false }
    )
}
