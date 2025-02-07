package com.cjmobileapps.quidditchplayersandroid.ui.playerlist

import androidx.lifecycle.SavedStateHandle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cjmobileapps.quidditchplayersandroid.data.MockDataFromCPP
import com.cjmobileapps.quidditchplayersandroid.data.model.HouseName
import com.cjmobileapps.quidditchplayersandroid.data.model.PlayerEntity
import com.cjmobileapps.quidditchplayersandroid.data.model.ResponseWrapper
import com.cjmobileapps.quidditchplayersandroid.data.model.Status
import com.cjmobileapps.quidditchplayersandroid.data.model.toPlayersState
import com.cjmobileapps.quidditchplayersandroid.data.quidditchplayers.QuidditchPlayersUseCase
import com.cjmobileapps.quidditchplayersandroid.testutil.BaseAndroidTest
import com.cjmobileapps.quidditchplayersandroid.ui.playerslist.viewmodel.PlayersListViewModel
import com.cjmobileapps.quidditchplayersandroid.ui.playerslist.viewmodel.PlayersListViewModelImpl
import com.cjmobileapps.quidditchplayersandroid.util.TestCoroutineDispatchers
import com.cjmobileapps.quidditchplayersandroid.util.TestTimeUtil
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.invoke
import io.mockk.justRun
import io.mockk.slot
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import java.net.HttpURLConnection
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class PlayerListViewModelImplTest : BaseAndroidTest() {
    private lateinit var playerLiveViewModel: PlayersListViewModel

    @MockK
    private lateinit var mockSavedStateHandle: SavedStateHandle

    @MockK
    private lateinit var mockQuidditchPlayersUseCase: QuidditchPlayersUseCase

    private val playerEntityResponseWrapperArgumentCaptor = slot<(ResponseWrapper<List<PlayerEntity>>) -> Unit>()

    private val testTimeUtil = TestTimeUtil

    private fun setupPlayerListViewModel() {
        testTimeUtil.resetTestTimeUtil()
        playerLiveViewModel =
            PlayersListViewModelImpl(
                savedStateHandle = mockSavedStateHandle,
                quidditchPlayersUseCase = mockQuidditchPlayersUseCase,
                timeUtil = testTimeUtil,
                coroutineDispatchers = TestCoroutineDispatchers,
            )
    }

    @After
    fun after() {
        unmockkAll()
    }

    @Test
    fun fetchPlayersHappyThenGoToPlayerDetailUiFlow() =
        runTest {
            // given
            val mockRavenPlayers = MockDataFromCPP.mockRavenclawPlayersEntities.toPlayersState()

            // when
            every { mockSavedStateHandle.get<String>("houseName") } returns HouseName.RAVENCLAW.name

            // then init setup
            setupPlayerListViewModel()
            var playerListState = playerLiveViewModel.getState()

            // verify
            assertTrue(playerListState is PlayersListViewModelImpl.PlayersListState.LoadingState)

            // when
            justRun { mockQuidditchPlayersUseCase.currentPlayer = any() }
            coEvery { mockQuidditchPlayersUseCase.fetchPlayersAndPositionsApis(HouseName.RAVENCLAW.name) } returns MockDataFromCPP.getMockTrueResponseWrapper()
            coEvery { mockQuidditchPlayersUseCase.getAllPlayersToDB(capture(playerEntityResponseWrapperArgumentCaptor)) } returns Unit
            coEvery { mockQuidditchPlayersUseCase.fetchStatusByHouseName(HouseName.RAVENCLAW.name) } returns MockDataFromCPP.getResponseWrapperMockStatus()

            // then
            setupPlayerListViewModel()
            playerEntityResponseWrapperArgumentCaptor.captured.invoke(MockDataFromCPP.mockRavenclawPlayersEntitiesResponseWrapper)
            playerListState = playerLiveViewModel.getState()
            val snackbarState = playerLiveViewModel.getSnackbarState()

            // verify
            assertTrue(snackbarState is PlayersListViewModelImpl.PlayersListSnackbarState.Idle)
            assertTrue(playerListState is PlayersListViewModelImpl.PlayersListState.PlayerListLoadedState)
            if (playerListState !is PlayersListViewModelImpl.PlayersListState.PlayerListLoadedState) return@runTest
            assertTrue(playerListState.playersNavRouteUi.value is PlayersListViewModelImpl.PlayersListNavRouteUi.Idle)

            playerListState.players.forEachIndexed { index, playerState ->
                assertEquals(
                    mockRavenPlayers[index].id,
                    playerState.id,
                )
                assertEquals(
                    mockRavenPlayers[index].favoriteSubject,
                    playerState.favoriteSubject,
                )
                assertEquals(
                    mockRavenPlayers[index].firstName,
                    playerState.firstName,
                )
                assertEquals(
                    mockRavenPlayers[index].lastName,
                    playerState.lastName,
                )
                assertEquals(
                    mockRavenPlayers[index].yearsPlayed,
                    playerState.yearsPlayed,
                )
                // todo fix unit test when adding reset time feature
//                if (index != 0) {
//                    Assertions.assertEquals(
//                        mockRavenPlayers[index].status.value,
//                        playerState.status.value,
//                    )
//                } else {
//                    Assertions.assertEquals(
//                        MockData.mockStatus().status,
//                        playerState.status.value,
//                    )
//                }
            }

            // then
            playerLiveViewModel.goToPlayerDetailUi(playerListState.players.first())

            // verify
            assertTrue(playerListState.playersNavRouteUi.value is PlayersListViewModelImpl.PlayersListNavRouteUi.GoToPlayerDetailUi)
            val playerListNavRouteUi = (playerListState.playersNavRouteUi.value as PlayersListViewModelImpl.PlayersListNavRouteUi.GoToPlayerDetailUi)
            assertEquals(
                mockRavenPlayers.first().id.toString(),
                playerListNavRouteUi.playerId,
            )
            assertEquals(
                "nav_player_detail/${mockRavenPlayers.first().id}",
                playerListNavRouteUi.getNavRouteWithArguments(),
            )

            // then
            playerLiveViewModel.resetNavRouteUiToIdle()

            // verify
            assertTrue(playerLiveViewModel.getPlayersListNavRouteUiState() is PlayersListViewModelImpl.PlayersListNavRouteUi.Idle)
        }

    @Test
    fun fetchPlayersHappyThenThrowErrorAtFetchPlayersAndPositionsApisErrorResponseFlow() =
        runTest {
            // given
            val mockRavenPlayers = MockDataFromCPP.mockRavenclawPlayersEntities.toPlayersState()

            // when
            every { mockSavedStateHandle.get<String>("houseName") } returns HouseName.RAVENCLAW.name

            // then init setup
            setupPlayerListViewModel()
            var playerListState = playerLiveViewModel.getState()

            // verify
            assertTrue(playerListState is PlayersListViewModelImpl.PlayersListState.LoadingState)

            // when
            coEvery { mockQuidditchPlayersUseCase.fetchPlayersAndPositionsApis(HouseName.RAVENCLAW.name) } returns MockDataFromCPP.getMockBooleanResponseWrapperGenericError()
            coEvery { mockQuidditchPlayersUseCase.getAllPlayersToDB(capture(playerEntityResponseWrapperArgumentCaptor)) } returns Unit
            coEvery { mockQuidditchPlayersUseCase.fetchStatusByHouseName(HouseName.RAVENCLAW.name) } returns MockDataFromCPP.getResponseWrapperMockStatus()

            // then
            setupPlayerListViewModel()
            playerEntityResponseWrapperArgumentCaptor.invoke(MockDataFromCPP.mockRavenclawPlayersEntitiesResponseWrapper)
            playerListState = playerLiveViewModel.getState()
            var snackbarState = playerLiveViewModel.getSnackbarState()

            // verify
            assertTrue(playerListState is PlayersListViewModelImpl.PlayersListState.PlayerListLoadedState)
            if (playerListState !is PlayersListViewModelImpl.PlayersListState.PlayerListLoadedState) return@runTest
            assertTrue(snackbarState is PlayersListViewModelImpl.PlayersListSnackbarState.UnableToGetPlayersListError)
            if (snackbarState !is PlayersListViewModelImpl.PlayersListSnackbarState.UnableToGetPlayersListError) return@runTest
            assertTrue(playerListState.playersNavRouteUi.value is PlayersListViewModelImpl.PlayersListNavRouteUi.Idle)
            assertNull(snackbarState.error)

            playerListState.players.forEachIndexed { index, playerState ->
                assertEquals(
                    mockRavenPlayers[index].id,
                    playerState.id,
                )
                assertEquals(
                    mockRavenPlayers[index].favoriteSubject,
                    playerState.favoriteSubject,
                )
                assertEquals(
                    mockRavenPlayers[index].firstName,
                    playerState.firstName,
                )
                assertEquals(
                    mockRavenPlayers[index].lastName,
                    playerState.lastName,
                )
                assertEquals(
                    mockRavenPlayers[index].yearsPlayed,
                    playerState.yearsPlayed,
                )
                if (index == 0) {
                    assertEquals(
                        MockDataFromCPP.getMockStatus().status,
                        playerState.status.value,
                    )
                }
            }

            // then
            playerLiveViewModel.resetNavRouteUiToIdle()
            playerLiveViewModel.resetSnackbarState()
            snackbarState = playerLiveViewModel.getSnackbarState()

            // verify
            assertTrue(snackbarState is PlayersListViewModelImpl.PlayersListSnackbarState.Idle)
            assertTrue(playerLiveViewModel.getPlayersListNavRouteUiState() is PlayersListViewModelImpl.PlayersListNavRouteUi.Idle)
        }

    //
    @Test
    fun fetchPlayersHappyThenThrowErrorAtGetHousesFromDBErrorResponseFlow() =
        runTest {
            // when
            every { mockSavedStateHandle.get<String>("houseName") } returns HouseName.RAVENCLAW.name

            // then init setup
            setupPlayerListViewModel()
            var playerListState = playerLiveViewModel.getState()

            // verify
            assertTrue(playerListState is PlayersListViewModelImpl.PlayersListState.LoadingState)

            // when
            coEvery { mockQuidditchPlayersUseCase.fetchPlayersAndPositionsApis(HouseName.RAVENCLAW.name) } returns MockDataFromCPP.getMockTrueResponseWrapper()
            coEvery { mockQuidditchPlayersUseCase.getAllPlayersToDB(capture(playerEntityResponseWrapperArgumentCaptor)) } returns Unit
            coEvery { mockQuidditchPlayersUseCase.fetchStatusByHouseName(HouseName.RAVENCLAW.name) } returns MockDataFromCPP.getResponseWrapperMockStatus()

            // then
            setupPlayerListViewModel()
            playerEntityResponseWrapperArgumentCaptor.invoke(MockDataFromCPP.mockRavenclawPlayersEntitiesResponseWrapperError)
            playerListState = playerLiveViewModel.getState()
            var snackbarState = playerLiveViewModel.getSnackbarState()

            // verify
            assertTrue(playerListState is PlayersListViewModelImpl.PlayersListState.PlayerListLoadedState)
            if (playerListState !is PlayersListViewModelImpl.PlayersListState.PlayerListLoadedState) return@runTest
            assertTrue(snackbarState is PlayersListViewModelImpl.PlayersListSnackbarState.UnableToGetPlayersListError)
            if (snackbarState !is PlayersListViewModelImpl.PlayersListSnackbarState.UnableToGetPlayersListError) return@runTest
            assertTrue(playerListState.playersNavRouteUi.value is PlayersListViewModelImpl.PlayersListNavRouteUi.Idle)
            assertNull(snackbarState.error)
            assertTrue(playerListState.players.isEmpty())

            // then
            playerLiveViewModel.resetNavRouteUiToIdle()
            playerLiveViewModel.resetSnackbarState()
            snackbarState = playerLiveViewModel.getSnackbarState()

            // verify
            assertTrue(snackbarState is PlayersListViewModelImpl.PlayersListSnackbarState.Idle)
            assertTrue(playerLiveViewModel.getPlayersListNavRouteUiState() is PlayersListViewModelImpl.PlayersListNavRouteUi.Idle)
        }

    @Test
    fun fetchStatusByHouseNameHappyFlow() =
        runTest {
            // given
            val mockRavenPlayers = MockDataFromCPP.mockRavenclawPlayersEntities.toPlayersState()


            // when
            coEvery { mockSavedStateHandle.get<String>("houseName") } returns HouseName.RAVENCLAW.name
            coEvery { mockQuidditchPlayersUseCase.fetchStatusByHouseName(HouseName.RAVENCLAW.name) } returns MockDataFromCPP.getResponseWrapperMockStatus()

            // then
            setupPlayerListViewModel()
            playerLiveViewModel.fetchStatusByHouseName(mockRavenPlayers)

            // verify
            assertEquals(
                MockDataFromCPP.getMockStatus().status,
                mockRavenPlayers.first().status.value,
            )
        }

    @Test
    fun fetchStatusByHouseNamePlayerNotFoundFlow() =
        runTest {
            // given
            val mockRavenPlayers = MockDataFromCPP.mockRavenclawPlayersEntities.toPlayersState()

            val name = "Harry Potters"
            val mockStatus =
                Status(
                    playerId = UUID.randomUUID(),
                    status = MockDataFromCPP.getStatus(name),
                )

            val mockStatusResponseWrapper =
                ResponseWrapper(
                    data = mockStatus,
                    statusCode = HttpURLConnection.HTTP_OK,
                )

            // when
            coEvery { mockSavedStateHandle.get<String>("houseName") } returns HouseName.RAVENCLAW.name
            coEvery { mockQuidditchPlayersUseCase.fetchStatusByHouseName(HouseName.RAVENCLAW.name) } returns mockStatusResponseWrapper

            // then
            setupPlayerListViewModel()
            playerLiveViewModel.fetchStatusByHouseName(mockRavenPlayers)

            // verify
            assertEquals(
                "",
                mockRavenPlayers.first().status.value,
            )
        }

    @Test
    fun fetchStatusByHouseNameReturnsErrorFlow() =
        runTest {
            // given
            val mockRavenPlayers = MockDataFromCPP.mockRavenclawPlayersEntities.toPlayersState()

            // when
            every { mockSavedStateHandle.get<String>("houseName") } returns HouseName.RAVENCLAW.name
            coEvery { mockQuidditchPlayersUseCase.fetchStatusByHouseName(HouseName.RAVENCLAW.name) } returns MockDataFromCPP.getMockStatusResponseWrapperGenericError()

            // then
            setupPlayerListViewModel()
            playerLiveViewModel.fetchStatusByHouseName(mockRavenPlayers)

            // verify
            assertEquals(
                "",
                mockRavenPlayers.first().status.value,
            )
        }

    @Test
    fun getTopBarTitleTest() =
        runTest {
            // when
            every { mockSavedStateHandle.get<String>("houseName") } returns HouseName.RAVENCLAW.name

            // then init setup
            setupPlayerListViewModel()
            val name = playerLiveViewModel.getTopBarTitle()

            // verify
            assertEquals(
                HouseName.RAVENCLAW.name,
                name,
            )
        }

    @Test
    fun houseViewmodelApisNeverInit() =
        runTest {
            // given
            val mockRavenPlayers = MockDataFromCPP.mockRavenclawPlayersEntities.toPlayersState()

            // when
            every { mockSavedStateHandle.get<String>("houseName") } returns ""

            // then init setup
            setupPlayerListViewModel()
            playerLiveViewModel.goToPlayerDetailUi(mockRavenPlayers.first())
            playerLiveViewModel.getPlayersListNavRouteUiState()
            playerLiveViewModel.resetNavRouteUiToIdle()
            val playerListState = playerLiveViewModel.getState()
            playerLiveViewModel.resetSnackbarState()
            val snackbarState = playerLiveViewModel.getSnackbarState()

            // verify
            assertTrue(playerListState is PlayersListViewModelImpl.PlayersListState.LoadingState)
            assertTrue(snackbarState is PlayersListViewModelImpl.PlayersListSnackbarState.Idle)
        }
}
