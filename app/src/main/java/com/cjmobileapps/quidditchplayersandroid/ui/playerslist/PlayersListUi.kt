package com.cjmobileapps.quidditchplayersandroid.ui.playerslist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.cjmobileapps.quidditchplayersandroid.R
import com.cjmobileapps.quidditchplayersandroid.ui.playerslist.viewmodel.PlayersListViewModel
import com.cjmobileapps.quidditchplayersandroid.ui.playerslist.viewmodel.PlayersListViewModelImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun PlayersListUi(
    navController: NavController,
    playersListViewModel: PlayersListViewModel,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    Scaffold(
        topBar = { },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Box {
            when (val state = playersListViewModel.getState()) {
                is PlayersListViewModelImpl.PlayersListState.LoadingState -> {

                }

                is PlayersListViewModelImpl.PlayersListState.PlayerListLoadedState -> {
                    PlayersListLoadedUi(
                        modifier = Modifier.padding(innerPadding),
                        playersListViewModel = playersListViewModel,
                        playersLoadedState = state,
                        navController = navController
                    )
                }
            }
        }

        val snackbarMessage: String? = when (val state = playersListViewModel.getSnackbarState()) {
            is PlayersListViewModelImpl.PlayersListSnackbarState.Idle -> null
            is PlayersListViewModelImpl.PlayersListSnackbarState.ShowGenericError -> state.error
                ?: stringResource(R.string.some_error_occurred)

            is PlayersListViewModelImpl.PlayersListSnackbarState.UnableToGetPlayersListError -> stringResource(
                R.string.unable_to_get_players
            )
        }

        if (snackbarMessage != null) {
            PlayerListSnackbar(
                message = snackbarMessage,
                coroutineScope = coroutineScope,
                snackbarHostState = snackbarHostState,
                playersListViewModel = playersListViewModel
            )
        }
    }
}

@Composable
fun PlayerListSnackbar(
    message: String,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    playersListViewModel: PlayersListViewModel
) {
    LaunchedEffect(key1 = message) {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(message = message)
            playersListViewModel.resetSnackbarState()
        }
    }
}

@Composable
fun PlayersListLoadedUi(
    modifier: Modifier,
    playersListViewModel: PlayersListViewModel,
    playersLoadedState: PlayersListViewModelImpl.PlayersListState.PlayerListLoadedState,
    navController: NavController
) {

    val players = playersLoadedState.players

    LazyColumn(
        modifier = modifier.padding(16.dp)
    ) {
        items(players) { player ->
            ElevatedCard(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .clickable { playersListViewModel.goToPlayerDetailUi(player.id) },
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row {
                        AsyncImage(
                            modifier = Modifier
                                .size(60.dp)
                                .padding(0.dp)
                                .border(
                                    BorderStroke(1.dp, Color.Black),
                                    RoundedCornerShape(4.dp)
                                )
                                .clip(RoundedCornerShape(4.dp)),
                            model = player.imageUrl,
                            contentDescription = stringResource(R.string.player_image),
                            contentScale = ContentScale.FillBounds
                        )

                        Column(
                            modifier = Modifier.padding(start = 12.dp)
                        ) {
                            Text(
                                text = player.firstName + " " + player.lastName,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )

                            Text(
                                text = player.position,
                                color = Color.DarkGray
                            )
                        }
                    }

                    Row(
                        modifier = modifier.padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = modifier.weight(1f),
                            text = stringResource(R.string.house),
                            color = Color.Gray
                        )
                        Text(
                            modifier = modifier.weight(3f),
                            text = player.house.name,
                            textAlign = TextAlign.Start,
                            color = Color.Black
                        )
                    }

                    Row(
                        modifier = modifier.padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = modifier.weight(1f),
                            text = stringResource(R.string.favorite_subject),
                            color = Color.Gray
                        )
                        Text(
                            modifier = modifier.weight(3f),
                            text = player.favoriteSubject,
                            textAlign = TextAlign.Start,
                            color = Color.Black
                        )
                    }

                    Row(
                        modifier = modifier.padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = modifier.weight(1f),
                            text = stringResource(R.string.years_played),
                            color = Color.Gray
                        )
                        Text(
                            modifier = modifier.weight(3f),
                            text = player.yearsPlayed.toString(),
                            textAlign = TextAlign.Start,
                            color = Color.Black
                        )
                    }

                    Row(
                        modifier = modifier.padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val statusState = player.status.value
                        val status = statusState.ifEmpty { stringResource(R.string.no_status) }

                        Text(
                            modifier = modifier.weight(1f),
                            text = stringResource(R.string.status),
                            color = Color.Gray
                        )
                        Text(
                            modifier = modifier.weight(3f),
                            text = status,
                            textAlign = TextAlign.Start,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }

    when (val navigateRouteUiValue = playersListViewModel.getPlayersListNavRouteUiState()) {
        is PlayersListViewModelImpl.PlayersListNavRouteUi.Idle -> {}
        is PlayersListViewModelImpl.PlayersListNavRouteUi.GoToPlayerDetailUi -> {
            navController.navigate(navigateRouteUiValue.getNavRouteWithArguments())
            playersListViewModel.resetNavRouteUiToIdle()
        }
    }
}
