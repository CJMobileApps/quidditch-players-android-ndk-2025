package com.cjmobileapps.quidditchplayersandroid.ui.playerslist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cjmobileapps.quidditchplayersandroid.ui.util.shimmerBrush

@Composable
fun PlayerListShimmerLoadingUi(modifier: Modifier) {

    val players = listOf("1", "2", "3", "4", "5", "6", "7")

    LazyColumn(
        modifier = modifier.padding(16.dp)
    ) {
        items(players) { player ->
            ElevatedCard(
                modifier = modifier
                    .fillMaxWidth()
                    .size(300.dp)
                    .padding(top = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                )
            ) {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .background(brush = shimmerBrush(showShimmer = true))
                ) { }
            }
        }
    }
}
