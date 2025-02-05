package com.cjmobileapps.quidditchplayersandroid.data.quidditchplayers

import com.cjmobileapps.quidditchplayersandroid.data.MockDataFromCPP
import com.cjmobileapps.quidditchplayersandroid.data.model.House
import com.cjmobileapps.quidditchplayersandroid.data.model.HouseName
import com.cjmobileapps.quidditchplayersandroid.data.model.PlayerEntity
import com.cjmobileapps.quidditchplayersandroid.data.model.toPlayerEntity
import com.cjmobileapps.quidditchplayersandroid.data.model.toPlayerState
import com.cjmobileapps.quidditchplayersandroid.data.model.toPlayersEntities
import com.cjmobileapps.quidditchplayersandroid.testutil.BaseAndroidTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test

class QuidditchPlayersUseCaseTest : BaseAndroidTest() {
    private lateinit var quidditchPlayersUseCase: QuidditchPlayersUseCase

    @MockK
    private lateinit var mockQuidditchPlayersRepository: QuidditchPlayersRepository

    private fun setupQuidditchPlayersUseCase() {
        quidditchPlayersUseCase =
            QuidditchPlayersUseCase(
                quidditchPlayersRepository = mockQuidditchPlayersRepository,
            )
    }

    @Test
    fun getHousesFromDB_happy_success_flow() =
        runTest {
            // given
            val mockGetAllHousesFlow: Flow<List<House>> =
                flow {
                    emit(MockDataFromCPP.getMockHouses())
                }

            // when
            coEvery { mockQuidditchPlayersRepository.getAllHousesFlow() } returns mockGetAllHousesFlow

            // then
            setupQuidditchPlayersUseCase()
            quidditchPlayersUseCase.getHousesFromDB { houses ->

                // verify
                assertEquals(
                    MockDataFromCPP.getMockHousesResponseWrapper(),
                    houses,
                )
            }
        }

    @Test
    fun getHousesFromDB_throw_exception() =
        runTest {
            // given
            val mockGetAllHousesFlow: Flow<List<House>> =
                flow {
                    throw Exception("Some error")
                }

            // when
            coEvery { mockQuidditchPlayersRepository.getAllHousesFlow() } returns mockGetAllHousesFlow

            // then
            setupQuidditchPlayersUseCase()
            quidditchPlayersUseCase.getHousesFromDB { houses ->

                // verify
                assertEquals(
                    MockDataFromCPP.mockHousesGenericErrorResponseWrapper,
                    houses,
                )
            }
        }

    @Test
    fun fetchHousesApi_happy_success_flow() =
        runTest {
            // when
            coEvery { mockQuidditchPlayersRepository.getAllHouses() } returns MockDataFromCPP.getMockHousesResponseWrapper()
            coEvery { mockQuidditchPlayersRepository.createAllHousesToDB(MockDataFromCPP.getMockHouses()) } returns Unit

            // then
            setupQuidditchPlayersUseCase()
            val housesApiResponse = quidditchPlayersUseCase.fetchHousesApi()

            // verify
            coVerify { mockQuidditchPlayersRepository.createAllHousesToDB(MockDataFromCPP.getMockHouses()) }
            assertEquals(
                MockDataFromCPP.mockTrueResponseWrapper,
                housesApiResponse,
            )
        }

    @Test
    fun fetchHousesApi_then_response_error_at_getAllHouses_error_flow() =
        runTest {
            // when
            coEvery { mockQuidditchPlayersRepository.getAllHouses() } returns MockDataFromCPP.mockHousesGenericErrorResponseWrapper

            // then
            setupQuidditchPlayersUseCase()
            val housesApiResponse = quidditchPlayersUseCase.fetchHousesApi()

            // verify
            coVerify(exactly = 0) { mockQuidditchPlayersRepository.createAllHousesToDB(MockDataFromCPP.getMockHouses()) }
            assertEquals(
                MockDataFromCPP.mockHousesGenericErrorResponseWrapper,
                housesApiResponse,
            )
        }

    @Test
    fun fetchHousesApi_throw_exception_at_createAllHousesToDB_error_flow() =
        runTest {
            // when
            coEvery { mockQuidditchPlayersRepository.getAllHouses() } returns MockDataFromCPP.getMockHousesResponseWrapper()
            coEvery { mockQuidditchPlayersRepository.createAllHousesToDB(MockDataFromCPP.getMockHouses()) } throws RuntimeException("Some error")

            // then
            setupQuidditchPlayersUseCase()
            val housesApiResponse = quidditchPlayersUseCase.fetchHousesApi()

            // verify
            assertEquals(
                MockDataFromCPP.mockHousesGenericErrorResponseWrapper,
                housesApiResponse,
            )
        }

    @Test
    fun fetchPlayersAndPositionsApis_happy_success_flow() =
        runTest {
            // when
            coEvery { mockQuidditchPlayersRepository.fetchPlayersAndPositions(HouseName.RAVENCLAW.name) } returns MockDataFromCPP.mockRavenclawPlayersAndPositionsResponseWrappers
            coEvery { mockQuidditchPlayersRepository.createPlayersByHouseToDB(MockDataFromCPP.ravenclawTeam().toPlayersEntities(MockDataFromCPP.getMockPositions())) } returns Unit

            // then
            setupQuidditchPlayersUseCase()
            val playersAndPositionsApisResponse = quidditchPlayersUseCase.fetchPlayersAndPositionsApis(HouseName.RAVENCLAW.name)

            // verify
            coVerify { mockQuidditchPlayersRepository.createPlayersByHouseToDB(MockDataFromCPP.ravenclawTeam().toPlayersEntities(MockDataFromCPP.getMockPositions())) }
            assertEquals(
                MockDataFromCPP.mockTrueResponseWrapper,
                playersAndPositionsApisResponse,
            )
        }

    @Test
    fun fetchPlayersAndPositionsApis_with_players_error_response_error_flow() =
        runTest {
            // when
            coEvery { mockQuidditchPlayersRepository.fetchPlayersAndPositions(HouseName.RAVENCLAW.name) } returns MockDataFromCPP.mockRavenclawPlayersErrorAndPositionsResponseWrappers

            // then
            setupQuidditchPlayersUseCase()
            val playersAndPositionsApisResponse = quidditchPlayersUseCase.fetchPlayersAndPositionsApis(HouseName.RAVENCLAW.name)

            // verify
            coVerify(exactly = 0) { mockQuidditchPlayersRepository.createPlayersByHouseToDB(MockDataFromCPP.ravenclawTeam().toPlayersEntities(MockDataFromCPP.getMockPositions())) }
            assertEquals(
                MockDataFromCPP.mockBooleanResponseWrapperGenericError,
                playersAndPositionsApisResponse,
            )
        }

    @Test
    fun fetchPlayersAndPositionsApis_with_positions_error_response_error_flow() =
        runTest {
            // when
            coEvery { mockQuidditchPlayersRepository.fetchPlayersAndPositions(HouseName.RAVENCLAW.name) } returns MockDataFromCPP.mockRavenclawPlayersAndPositionsErrorResponseWrappers

            // then
            setupQuidditchPlayersUseCase()
            val playersAndPositionsApisResponse = quidditchPlayersUseCase.fetchPlayersAndPositionsApis(HouseName.RAVENCLAW.name)

            // verify
            coVerify(exactly = 0) { mockQuidditchPlayersRepository.createPlayersByHouseToDB(MockDataFromCPP.ravenclawTeam().toPlayersEntities(MockDataFromCPP.getMockPositions())) }
            assertEquals(
                MockDataFromCPP.mockBooleanResponseWrapperGenericError,
                playersAndPositionsApisResponse,
            )
        }

    @Test
    fun fetchPlayersAndPositionsApis_throw_exception_at_createAllHousesToDB_error_flow() =
        runTest {
            // when
            coEvery { mockQuidditchPlayersRepository.fetchPlayersAndPositions(HouseName.RAVENCLAW.name) } returns MockDataFromCPP.mockRavenclawPlayersAndPositionsResponseWrappers
            coEvery { mockQuidditchPlayersRepository.createPlayersByHouseToDB(MockDataFromCPP.ravenclawTeam().toPlayersEntities(MockDataFromCPP.getMockPositions())) } throws RuntimeException("Some error")

            // then
            setupQuidditchPlayersUseCase()
            val playersAndPositionsApisResponse = quidditchPlayersUseCase.fetchPlayersAndPositionsApis(HouseName.RAVENCLAW.name)

            // verify
            coVerify(exactly = 1) { mockQuidditchPlayersRepository.createPlayersByHouseToDB(MockDataFromCPP.ravenclawTeam().toPlayersEntities(MockDataFromCPP.getMockPositions())) }
            assertEquals(
                MockDataFromCPP.mockBooleanResponseWrapperGenericError,
                playersAndPositionsApisResponse,
            )
        }

    @Test
    fun getAllPlayersToDB_happy_success_flow() =
        runTest {
            // given
            val mockGetAllPlayersPlayerEntityFlow: Flow<List<PlayerEntity>> =
                flow {
                    emit(MockDataFromCPP.ravenclawTeam().toPlayersEntities(MockDataFromCPP.getMockPositions()))
                }

            // when
            coEvery { mockQuidditchPlayersRepository.getAllPlayersFlow() } returns mockGetAllPlayersPlayerEntityFlow

            // then
            setupQuidditchPlayersUseCase()
            quidditchPlayersUseCase.getAllPlayersToDB { players ->

                // verify
                assertEquals(
                    MockDataFromCPP.mockRavenclawPlayersEntitiesResponseWrapper,
                    players,
                )
            }
        }

    @Test
    fun getAllPlayersToDB_throw_getAllPlayersFlow_error_flow() =
        runTest {
            // given
            val mockGetAllPlayersPlayerEntityFlow: Flow<List<PlayerEntity>> =
                flow {
                    throw Exception("Some error")
                }

            // when
            coEvery { mockQuidditchPlayersRepository.getAllPlayersFlow() } returns mockGetAllPlayersPlayerEntityFlow

            // then
            setupQuidditchPlayersUseCase()
            quidditchPlayersUseCase.getAllPlayersToDB { players ->

                // verify
                assertEquals(
                    MockDataFromCPP.mockRavenclawPlayersEntitiesResponseWrapperError,
                    players,
                )
            }
        }

    @Test
    fun fetchStatusByHouseName_happy_success_flow() =
        runTest {
            // when
            coEvery { mockQuidditchPlayersRepository.fetchStatusByHouseName(HouseName.RAVENCLAW.name) } returns MockDataFromCPP.mockStatusResponseWrapper

            // then
            setupQuidditchPlayersUseCase()
            val status = quidditchPlayersUseCase.fetchStatusByHouseName(HouseName.RAVENCLAW.name)

            // verify
            assertEquals(
                MockDataFromCPP.mockStatusResponseWrapper,
                status,
            )
        }

    @Test
    fun fetchStatusByPlayerId_happy_success_flow() =
        runTest {
            // given
            val playerId = MockDataFromCPP.ravenclawTeam().first().id.toString()

            // when
            coEvery { mockQuidditchPlayersRepository.fetchStatusByPlayerId(playerId) } returns MockDataFromCPP.mockStatusResponseWrapper

            // then
            setupQuidditchPlayersUseCase()
            val status = quidditchPlayersUseCase.fetchStatusByPlayerId(playerId)

            // verify
            assertEquals(
                MockDataFromCPP.mockStatusResponseWrapper,
                status,
            )
        }

    @Test
    fun set_currentPlayer() {
        val playerState =
            MockDataFromCPP
                .ravenclawTeam()
                .first()
                .toPlayerEntity(MockDataFromCPP.getMockPositions())
                .toPlayerState()

        // then
        setupQuidditchPlayersUseCase()
        quidditchPlayersUseCase.currentPlayer = playerState

        // verify
        assertEquals(
            playerState,
            quidditchPlayersUseCase.currentPlayer,
        )
    }
}
