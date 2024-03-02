package com.cjmobileapps.quidditchplayersandroid.ui.houses

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cjmobileapps.quidditchplayersandroid.ui.util.shimmerBrush

@Composable
fun HousesShimmerLoadingUi(modifier: Modifier) {

    val houses = listOf("1", "2", "3", "4")

    LazyVerticalGrid(
        modifier = modifier
            .padding(16.dp),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(houses) {
            ElevatedCard(
                modifier = Modifier
                    .size(200.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush = shimmerBrush(showShimmer = true))
                ) { }
            }
        }
    }
}
