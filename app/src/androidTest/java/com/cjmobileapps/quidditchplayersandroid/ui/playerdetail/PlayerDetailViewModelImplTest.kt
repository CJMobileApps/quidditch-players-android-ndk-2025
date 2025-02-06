package com.cjmobileapps.quidditchplayersandroid.ui.playerdetail

import androidx.lifecycle.SavedStateHandle
import com.cjmobileapps.quidditchplayersandroid.data.MockDataFromCPP
import com.cjmobileapps.quidditchplayersandroid.data.model.toPlayersState
import com.cjmobileapps.quidditchplayersandroid.data.quidditchplayers.QuidditchPlayersUseCase
import com.cjmobileapps.quidditchplayersandroid.testutil.BaseAndroidTest
import com.cjmobileapps.quidditchplayersandroid.ui.playerdetail.viewmodel.PlayerDetailViewModel
import com.cjmobileapps.quidditchplayersandroid.ui.playerdetail.viewmodel.PlayerDetailViewModelImpl
import com.cjmobileapps.quidditchplayersandroid.util.TestCoroutineDispatchers
import com.cjmobileapps.quidditchplayersandroid.util.TestTimeUtil
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test

class PlayerDetailViewModelImplTest : BaseAndroidTest() {
    private lateinit var playerDetailViewModel: PlayerDetailViewModel

    @MockK
    private lateinit var mockSavedStateHandle: SavedStateHandle

    @MockK
    private lateinit var mockQuidditchPlayersUseCase: QuidditchPlayersUseCase

    private val testTimeUtil = TestTimeUtil

    private fun setupPlayerDetailViewModel() {
        testTimeUtil.resetTestTimeUtil()
        playerDetailViewModel =
            PlayerDetailViewModelImpl(
                quidditchPlayersUseCase = mockQuidditchPlayersUseCase,
                savedStateHandle = mockSavedStateHandle,
                timeUtil = testTimeUtil,
                coroutineDispatchers = TestCoroutineDispatchers,
            )
    }

    @Test
    fun fetch_player_happy_flow() =
        runTest {
            // given
            val mockRavenPlayer = MockDataFromCPP.mockRavenclawPlayersEntities.toPlayersState().first()
            val mockRavenPlayerId = mockRavenPlayer.id.toString()

            // when
            every { mockSavedStateHandle.get<String>("playerId") } returns mockRavenPlayerId
            every { mockQuidditchPlayersUseCase.currentPlayer } returns mockRavenPlayer
            coEvery { mockQuidditchPlayersUseCase.fetchStatusByPlayerId(mockRavenPlayerId) } returns MockDataFromCPP.mockStatusResponseWrapper

            // then init setup
            setupPlayerDetailViewModel()
            val playerDetailState = playerDetailViewModel.getState()
            val snackbarState = playerDetailViewModel.getSnackbarState()

            // verify
            assertTrue(snackbarState is PlayerDetailViewModelImpl.PlayerDetailSnackbarState.Idle)
            assertTrue(playerDetailState is PlayerDetailViewModelImpl.PlayerDetailState.PlayerDetailLoadedState)
            if (playerDetailState !is PlayerDetailViewModelImpl.PlayerDetailState.PlayerDetailLoadedState) return@runTest
            val player = playerDetailState.player
            val topBarTitle = playerDetailViewModel.getTopBarTitle()

            assertEquals(
                mockRavenPlayer.id,
                player?.id,
            )
            assertEquals(
                mockRavenPlayer.favoriteSubject,
                player?.favoriteSubject,
            )
            assertEquals(
                mockRavenPlayer.firstName,
                player?.firstName,
            )
            assertEquals(
                mockRavenPlayer.lastName,
                player?.lastName,
            )
            assertEquals(
                mockRavenPlayer.yearsPlayed,
                player?.yearsPlayed,
            )
            assertEquals(
                mockRavenPlayer.status.value,
                player?.status?.value,
            )
            assertEquals(
                mockRavenPlayer.getFullName(),
                topBarTitle,
            )
        }

    @Test
    fun fetch_player_then_return_status_error_flow() =
        runTest {
            // given
            val mockRavenPlayer = MockDataFromCPP.mockRavenclawPlayersEntities.toPlayersState().first()
            val mockRavenPlayerId = mockRavenPlayer.id.toString()

            // when
            every { mockSavedStateHandle.get<String>("playerId") } returns mockRavenPlayerId
            every { mockQuidditchPlayersUseCase.currentPlayer } returns mockRavenPlayer
            coEvery { mockQuidditchPlayersUseCase.fetchStatusByPlayerId(mockRavenPlayerId) } returns MockDataFromCPP.getMockStatusResponseWrapperGenericError()

            // then init setup
            setupPlayerDetailViewModel()
            val playerDetailState = playerDetailViewModel.getState()
            val snackbarState = playerDetailViewModel.getSnackbarState()

            // verify
            assertTrue(snackbarState is PlayerDetailViewModelImpl.PlayerDetailSnackbarState.Idle)
            assertTrue(playerDetailState is PlayerDetailViewModelImpl.PlayerDetailState.PlayerDetailLoadedState)
            if (playerDetailState !is PlayerDetailViewModelImpl.PlayerDetailState.PlayerDetailLoadedState) return@runTest
            val player = playerDetailState.player
            val topBarTitle = playerDetailViewModel.getTopBarTitle()

            assertEquals(
                mockRavenPlayer.id,
                player?.id,
            )
            assertEquals(
                mockRavenPlayer.favoriteSubject,
                player?.favoriteSubject,
            )
            assertEquals(
                mockRavenPlayer.firstName,
                player?.firstName,
            )
            assertEquals(
                mockRavenPlayer.lastName,
                player?.lastName,
            )
            assertEquals(
                mockRavenPlayer.yearsPlayed,
                player?.yearsPlayed,
            )
            assertEquals(
                mockRavenPlayer.status.value,
                player?.status?.value,
            )
            assertEquals(
                mockRavenPlayer.getFullName(),
                topBarTitle,
            )
        }

    @Test
    fun fetch_player_then_throw_status_error_flow() =
        runTest {
            // given
            val mockRavenPlayer = MockDataFromCPP.mockRavenclawPlayersEntities.toPlayersState().first()
            val mockRavenPlayerId = mockRavenPlayer.id.toString()

            // when
            every { mockSavedStateHandle.get<String>("playerId") } returns mockRavenPlayerId
            every { mockQuidditchPlayersUseCase.currentPlayer } returns mockRavenPlayer
            coEvery { mockQuidditchPlayersUseCase.fetchStatusByPlayerId(mockRavenPlayerId) } throws RuntimeException("Some error")

            // then init setup
            setupPlayerDetailViewModel()
            val playerDetailState = playerDetailViewModel.getState()
            val snackbarState = playerDetailViewModel.getSnackbarState()

            // verify
            // this assertion doesn't work not sure why
            assertTrue(snackbarState is PlayerDetailViewModelImpl.PlayerDetailSnackbarState.ShowGenericError)
            assertTrue(playerDetailState is PlayerDetailViewModelImpl.PlayerDetailState.PlayerDetailLoadedState)
            if (playerDetailState !is PlayerDetailViewModelImpl.PlayerDetailState.PlayerDetailLoadedState) return@runTest
            val player = playerDetailState.player
            val topBarTitle = playerDetailViewModel.getTopBarTitle()

            assertEquals(
                mockRavenPlayer.id,
                player?.id,
            )
            assertEquals(
                mockRavenPlayer.favoriteSubject,
                player?.favoriteSubject,
            )
            assertEquals(
                mockRavenPlayer.firstName,
                player?.firstName,
            )
            assertEquals(
                mockRavenPlayer.lastName,
                player?.lastName,
            )
            assertEquals(
                mockRavenPlayer.yearsPlayed,
                player?.yearsPlayed,
            )
            assertEquals(
                mockRavenPlayer.status.value,
                player?.status?.value,
            )
            assertEquals(
                mockRavenPlayer.getFullName(),
                topBarTitle,
            )
        }

    @Test
    fun throw_fetching_player_is_null_flow() =
        runTest {
            // given
            val mockRavenPlayer = MockDataFromCPP.mockRavenclawPlayersEntities.toPlayersState().first()
            val mockRavenPlayerId = mockRavenPlayer.id.toString()

            // when
            every { mockSavedStateHandle.get<String>("playerId") } returns mockRavenPlayerId
            every { mockQuidditchPlayersUseCase.currentPlayer } returns null
            coEvery { mockQuidditchPlayersUseCase.fetchStatusByPlayerId(mockRavenPlayerId) } returns MockDataFromCPP.mockStatusResponseWrapper

            // then init setup
            setupPlayerDetailViewModel()
            val playerDetailState = playerDetailViewModel.getState()
            var snackbarState = playerDetailViewModel.getSnackbarState()

            // verify
            assertTrue(playerDetailState is PlayerDetailViewModelImpl.PlayerDetailState.PlayerDetailLoadedState)
            if (playerDetailState !is PlayerDetailViewModelImpl.PlayerDetailState.PlayerDetailLoadedState) return@runTest
            assertTrue(snackbarState is PlayerDetailViewModelImpl.PlayerDetailSnackbarState.UnableToGetPlayerError)

            // then
            playerDetailViewModel.resetSnackbarState()
            snackbarState = playerDetailViewModel.getSnackbarState()
            assertTrue(snackbarState is PlayerDetailViewModelImpl.PlayerDetailSnackbarState.Idle)
        }
}
