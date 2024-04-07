package com.cjmobileapps.quidditchplayersandroid.ui.playerlist.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.cjmobileapps.quidditchplayersandroid.data.MockData
import com.cjmobileapps.quidditchplayersandroid.data.model.HouseName
import com.cjmobileapps.quidditchplayersandroid.data.model.PlayerEntity
import com.cjmobileapps.quidditchplayersandroid.data.model.ResponseWrapper
import com.cjmobileapps.quidditchplayersandroid.data.model.toPlayersState
import com.cjmobileapps.quidditchplayersandroid.data.quidditchplayers.QuidditchPlayersUseCase
import com.cjmobileapps.quidditchplayersandroid.testutil.BaseTest
import com.cjmobileapps.quidditchplayersandroid.testutil.TestCoroutineDispatchers
import com.cjmobileapps.quidditchplayersandroid.ui.playerslist.viewmodel.PlayersListViewModel
import com.cjmobileapps.quidditchplayersandroid.ui.playerslist.viewmodel.PlayersListViewModelImpl
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.kotlin.argumentCaptor

class PlayerListViewModelImplTest : BaseTest() {
    private lateinit var playerLiveViewModel: PlayersListViewModel

    @Mock
    private lateinit var mockSavedStateHandle: SavedStateHandle

    @Mock
    private lateinit var mockQuidditchPlayersUseCase: QuidditchPlayersUseCase

    private val playerEntityResponseWrapperArgumentCaptor =
        argumentCaptor<(ResponseWrapper<List<PlayerEntity>>) -> Unit>()

    private fun setupPlayerListViewModel() {
        playerLiveViewModel =
            PlayersListViewModelImpl(
                savedStateHandle = mockSavedStateHandle,
                quidditchPlayersUseCase = mockQuidditchPlayersUseCase,
                coroutineDispatchers = TestCoroutineDispatchers,
            )
    }

    @Test
    fun `fetch players happy flow`() =
        runTest {
            // given
            val mockRavenPlayers = MockData.mockRavenclawPlayersEntities.toPlayersState()

            // when
            Mockito.`when`(mockSavedStateHandle.get<String>("houseName")).thenReturn(HouseName.RAVENCLAW.name)

            // then init setup
            setupPlayerListViewModel()
            var playerListState = playerLiveViewModel.getState()

            // verify
            Assertions.assertTrue(playerListState is PlayersListViewModelImpl.PlayersListState.LoadingState)

            // when
            Mockito.`when`(mockQuidditchPlayersUseCase.fetchPlayersAndPositionsApis(HouseName.RAVENCLAW.name)).thenReturn(MockData.mockTrueResponseWrapper)
            Mockito.`when`(mockQuidditchPlayersUseCase.getAllPlayersToDB(playerEntityResponseWrapperArgumentCaptor.capture())).thenReturn(Unit)

            // then
            setupPlayerListViewModel()
            playerEntityResponseWrapperArgumentCaptor.firstValue.invoke(MockData.mockRavenclawPlayersEntitiesResponseWrapper)
            playerListState = playerLiveViewModel.getState()
            val snackbarState = playerLiveViewModel.getSnackbarState()

            // verify
            Assertions.assertTrue(snackbarState is PlayersListViewModelImpl.PlayersListSnackbarState.Idle)
            Assertions.assertTrue(playerListState is PlayersListViewModelImpl.PlayersListState.PlayerListLoadedState)
            if (playerListState !is PlayersListViewModelImpl.PlayersListState.PlayerListLoadedState) return@runTest

            playerListState.players.forEachIndexed { index, playerState ->
                Assertions.assertEquals(
                    mockRavenPlayers[index].id,
                    playerState.id,
                )
                Assertions.assertEquals(
                    mockRavenPlayers[index].favoriteSubject,
                    playerState.favoriteSubject,
                )
                Assertions.assertEquals(
                    mockRavenPlayers[index].firstName,
                    playerState.firstName,
                )
                Assertions.assertEquals(
                    mockRavenPlayers[index].lastName,
                    playerState.lastName,
                )
                Assertions.assertEquals(
                    mockRavenPlayers[index].yearsPlayed,
                    playerState.yearsPlayed,
                )
                Assertions.assertEquals(
                    mockRavenPlayers[index].status.value,
                    playerState.status.value,
                )
            }

            // todo add more unit test getStatuesForPlayer()
        }
}
