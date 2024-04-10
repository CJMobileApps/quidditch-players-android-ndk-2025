package com.cjmobileapps.quidditchplayersandroid.ui.playerlist.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.cjmobileapps.quidditchplayersandroid.data.MockData
import com.cjmobileapps.quidditchplayersandroid.data.model.HouseName
import com.cjmobileapps.quidditchplayersandroid.data.model.PlayerEntity
import com.cjmobileapps.quidditchplayersandroid.data.model.ResponseWrapper
import com.cjmobileapps.quidditchplayersandroid.data.model.Status
import com.cjmobileapps.quidditchplayersandroid.data.model.toPlayersState
import com.cjmobileapps.quidditchplayersandroid.data.quidditchplayers.QuidditchPlayersUseCase
import com.cjmobileapps.quidditchplayersandroid.testutil.BaseTest
import com.cjmobileapps.quidditchplayersandroid.ui.playerslist.viewmodel.PlayersListViewModel
import com.cjmobileapps.quidditchplayersandroid.ui.playerslist.viewmodel.PlayersListViewModelImpl
import com.cjmobileapps.quidditchplayersandroid.util.TestCoroutineDispatchers
import com.cjmobileapps.quidditchplayersandroid.util.TestTimeUtil
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.kotlin.argumentCaptor
import java.net.HttpURLConnection
import java.util.UUID

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
                timeUtil = TestTimeUtil,
                coroutineDispatchers = TestCoroutineDispatchers,
            )
    }

    @Test
    fun `fetch players happy then goToPlayerDetailUi flow`() =
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
            Mockito.`when`(mockQuidditchPlayersUseCase.fetchStatusByHouseName(HouseName.RAVENCLAW.name)).thenReturn(MockData.mockStatusResponseWrapper)

            // then
            setupPlayerListViewModel()
            playerEntityResponseWrapperArgumentCaptor.firstValue.invoke(MockData.mockRavenclawPlayersEntitiesResponseWrapper)
            playerListState = playerLiveViewModel.getState()
            val snackbarState = playerLiveViewModel.getSnackbarState()

            // verify
            Assertions.assertTrue(snackbarState is PlayersListViewModelImpl.PlayersListSnackbarState.Idle)
            Assertions.assertTrue(playerListState is PlayersListViewModelImpl.PlayersListState.PlayerListLoadedState)
            if (playerListState !is PlayersListViewModelImpl.PlayersListState.PlayerListLoadedState) return@runTest
            Assertions.assertTrue(playerListState.playersNavRouteUi.value is PlayersListViewModelImpl.PlayersListNavRouteUi.Idle)

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
                if(index != 0) {
                    Assertions.assertEquals(
                        mockRavenPlayers[index].status.value,
                        playerState.status.value,
                    )
                } else {
                    Assertions.assertEquals(
                        MockData.mockStatus().status,
                        playerState.status.value,
                    )
                }
            }

            // then
            playerLiveViewModel.goToPlayerDetailUi(playerListState.players.first())

            // verify
            Assertions.assertTrue(playerListState.playersNavRouteUi.value is PlayersListViewModelImpl.PlayersListNavRouteUi.GoToPlayerDetailUi)
            val playerListNavRouteUi = (playerListState.playersNavRouteUi.value as PlayersListViewModelImpl.PlayersListNavRouteUi.GoToPlayerDetailUi)
            Assertions.assertEquals(
                mockRavenPlayers.first().id.toString(),
                playerListNavRouteUi.playerId,
            )
            Assertions.assertEquals(
                "nav_player_detail/${mockRavenPlayers.first().id}",
                playerListNavRouteUi.getNavRouteWithArguments(),
            )

            // then
            playerLiveViewModel.resetNavRouteUiToIdle()

            // verify
            Assertions.assertTrue(playerLiveViewModel.getPlayersListNavRouteUiState() is PlayersListViewModelImpl.PlayersListNavRouteUi.Idle)
        }

    @Test
    fun `fetch players happy then throw error at fetchPlayersAndPositionsApis error response flow`() =
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
            Mockito.`when`(mockQuidditchPlayersUseCase.fetchPlayersAndPositionsApis(HouseName.RAVENCLAW.name)).thenReturn(MockData.mockBooleanResponseWrapperGenericError)
            Mockito.`when`(mockQuidditchPlayersUseCase.getAllPlayersToDB(playerEntityResponseWrapperArgumentCaptor.capture())).thenReturn(Unit)
            Mockito.`when`(mockQuidditchPlayersUseCase.fetchStatusByHouseName(HouseName.RAVENCLAW.name)).thenReturn(MockData.mockStatusResponseWrapper)

            // then
            setupPlayerListViewModel()
            playerEntityResponseWrapperArgumentCaptor.firstValue.invoke(MockData.mockRavenclawPlayersEntitiesResponseWrapper)
            playerListState = playerLiveViewModel.getState()
            var snackbarState = playerLiveViewModel.getSnackbarState()

            // verify
            Assertions.assertTrue(playerListState is PlayersListViewModelImpl.PlayersListState.PlayerListLoadedState)
            if (playerListState !is PlayersListViewModelImpl.PlayersListState.PlayerListLoadedState) return@runTest
            Assertions.assertTrue(snackbarState is PlayersListViewModelImpl.PlayersListSnackbarState.UnableToGetPlayersListError)
            if (snackbarState !is PlayersListViewModelImpl.PlayersListSnackbarState.UnableToGetPlayersListError) return@runTest
            Assertions.assertTrue(playerListState.playersNavRouteUi.value is PlayersListViewModelImpl.PlayersListNavRouteUi.Idle)
            Assertions.assertNull(snackbarState.error)

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

            // then
            playerLiveViewModel.resetNavRouteUiToIdle()
            playerLiveViewModel.resetSnackbarState()
            snackbarState = playerLiveViewModel.getSnackbarState()

            // verify
            Assertions.assertTrue(snackbarState is PlayersListViewModelImpl.PlayersListSnackbarState.Idle)
            Assertions.assertTrue(playerLiveViewModel.getPlayersListNavRouteUiState() is PlayersListViewModelImpl.PlayersListNavRouteUi.Idle)
        }

    @Test
    fun `fetch players happy then throw error at getHousesFromDB() error response flow`() =
        runTest {
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
            Mockito.`when`(mockQuidditchPlayersUseCase.fetchStatusByHouseName(HouseName.RAVENCLAW.name)).thenReturn(MockData.mockStatusResponseWrapper)

            // then
            setupPlayerListViewModel()
            playerEntityResponseWrapperArgumentCaptor.firstValue.invoke(MockData.mockRavenclawPlayersEntitiesResponseWrapperError)
            playerListState = playerLiveViewModel.getState()
            var snackbarState = playerLiveViewModel.getSnackbarState()

            // verify
            Assertions.assertTrue(playerListState is PlayersListViewModelImpl.PlayersListState.PlayerListLoadedState)
            if (playerListState !is PlayersListViewModelImpl.PlayersListState.PlayerListLoadedState) return@runTest
            Assertions.assertTrue(snackbarState is PlayersListViewModelImpl.PlayersListSnackbarState.UnableToGetPlayersListError)
            if (snackbarState !is PlayersListViewModelImpl.PlayersListSnackbarState.UnableToGetPlayersListError) return@runTest
            Assertions.assertTrue(playerListState.playersNavRouteUi.value is PlayersListViewModelImpl.PlayersListNavRouteUi.Idle)
            Assertions.assertNull(snackbarState.error)
            Assertions.assertTrue(playerListState.players.isEmpty())

            // then
            playerLiveViewModel.resetNavRouteUiToIdle()
            playerLiveViewModel.resetSnackbarState()
            snackbarState = playerLiveViewModel.getSnackbarState()

            // verify
            Assertions.assertTrue(snackbarState is PlayersListViewModelImpl.PlayersListSnackbarState.Idle)
            Assertions.assertTrue(playerLiveViewModel.getPlayersListNavRouteUiState() is PlayersListViewModelImpl.PlayersListNavRouteUi.Idle)
        }

    @Test
    fun `fetchStatusByHouseName() happy flow`() =
        runTest {
            // given
            val mockRavenPlayers = MockData.mockRavenclawPlayersEntities.toPlayersState()

            // when
            Mockito.`when`(mockSavedStateHandle.get<String>("houseName")).thenReturn(HouseName.RAVENCLAW.name)
            Mockito.`when`(mockQuidditchPlayersUseCase.fetchStatusByHouseName(HouseName.RAVENCLAW.name)).thenReturn(MockData.mockStatusResponseWrapper)

            // then
            setupPlayerListViewModel()
            playerLiveViewModel.fetchStatusByHouseName(mockRavenPlayers)

            // verify
            Assertions.assertEquals(
                MockData.mockStatus().status,
                mockRavenPlayers.first().status.value,
            )
        }

    @Test
    fun `fetchStatusByHouseName() player not found flow`() =
        runTest {
            // given
            val mockRavenPlayers = MockData.mockRavenclawPlayersEntities.toPlayersState()

            val name = "Harry Potters"
            val mockStatus =
                Status(
                    playerId = UUID.randomUUID(),
                    status = MockData.getStatus(name),
                )

            val mockStatusResponseWrapper =
                ResponseWrapper(
                    data = mockStatus,
                    statusCode = HttpURLConnection.HTTP_OK,
                )

            // when
            Mockito.`when`(mockSavedStateHandle.get<String>("houseName")).thenReturn(HouseName.RAVENCLAW.name)
            Mockito.`when`(mockQuidditchPlayersUseCase.fetchStatusByHouseName(HouseName.RAVENCLAW.name)).thenReturn(mockStatusResponseWrapper)

            // then
            setupPlayerListViewModel()
            playerLiveViewModel.fetchStatusByHouseName(mockRavenPlayers)

            // verify
            Assertions.assertEquals(
                "",
                mockRavenPlayers.first().status.value,
            )
        }

    @Test
    fun `fetchStatusByHouseName() returns error flow`() =
        runTest {
            // given
            val mockRavenPlayers = MockData.mockRavenclawPlayersEntities.toPlayersState()

            // when
            Mockito.`when`(mockSavedStateHandle.get<String>("houseName")).thenReturn(HouseName.RAVENCLAW.name)
            Mockito.`when`(mockQuidditchPlayersUseCase.fetchStatusByHouseName(HouseName.RAVENCLAW.name)).thenReturn(MockData.mockStatusResponseWrapperGenericError)

            // then
            setupPlayerListViewModel()
            playerLiveViewModel.fetchStatusByHouseName(mockRavenPlayers)

            // verify
            Assertions.assertEquals(
                "",
                mockRavenPlayers.first().status.value,
            )
        }

    @Test
    fun `getTopBarTitle() test`() =
        runTest {
            // when
            Mockito.`when`(mockSavedStateHandle.get<String>("houseName")).thenReturn(HouseName.RAVENCLAW.name)

            // then init setup
            setupPlayerListViewModel()
            val name = playerLiveViewModel.getTopBarTitle()

            // verify
            Assertions.assertEquals(
                HouseName.RAVENCLAW.name,
                name,
            )
        }

    @Test
    fun `house viewmodel apis never init`() =
        runTest {
            // given
            val mockRavenPlayers = MockData.mockRavenclawPlayersEntities.toPlayersState()

            // when
            Mockito.`when`(mockSavedStateHandle.get<String>("houseName")).thenReturn("")

            // then init setup
            setupPlayerListViewModel()
            playerLiveViewModel.goToPlayerDetailUi(mockRavenPlayers.first())
            playerLiveViewModel.getPlayersListNavRouteUiState()
            playerLiveViewModel.resetNavRouteUiToIdle()
            val playerListState = playerLiveViewModel.getState()
            playerLiveViewModel.resetSnackbarState()
            val snackbarState = playerLiveViewModel.getSnackbarState()

            // verify
            Assertions.assertTrue(playerListState is PlayersListViewModelImpl.PlayersListState.LoadingState)
            Assertions.assertTrue(snackbarState is PlayersListViewModelImpl.PlayersListSnackbarState.Idle)
        }
}
