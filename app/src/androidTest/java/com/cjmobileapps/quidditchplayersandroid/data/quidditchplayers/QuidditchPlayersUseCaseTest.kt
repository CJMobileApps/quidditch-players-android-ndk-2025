package com.cjmobileapps.quidditchplayersandroid.data.quidditchplayers

import com.cjmobileapps.quidditchplayersandroid.data.MockData
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
                    emit(MockData.mockHouses)
                }

            // when
            coEvery { mockQuidditchPlayersRepository.getAllHousesFlow() } returns mockGetAllHousesFlow

            // then
            setupQuidditchPlayersUseCase()
            quidditchPlayersUseCase.getHousesFromDB { houses ->

                // verify
                assertEquals(
                    MockData.mockHousesResponseWrapper,
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
                    MockData.mockHousesGenericErrorResponseWrapper,
                    houses,
                )
            }
        }

    @Test
    fun fetchHousesApi_happy_success_flow() =
        runTest {
            // when
            coEvery { mockQuidditchPlayersRepository.getAllHouses() } returns MockData.mockHousesResponseWrapper
            coEvery { mockQuidditchPlayersRepository.createAllHousesToDB(MockDataFromCPP.getMockHouses()) } returns Unit

            // then
            setupQuidditchPlayersUseCase()
            val housesApiResponse = quidditchPlayersUseCase.fetchHousesApi()

            // verify
            coVerify { mockQuidditchPlayersRepository.createAllHousesToDB(MockData.mockHouses) }
            assertEquals(
                MockData.mockTrueResponseWrapper,
                housesApiResponse,
            )
        }

    @Test
    fun fetchHousesApi_then_response_error_at_getAllHouses_error_flow() =
        runTest {
            // when
            coEvery { mockQuidditchPlayersRepository.getAllHouses() } returns MockData.mockHousesGenericErrorResponseWrapper

            // then
            setupQuidditchPlayersUseCase()
            val housesApiResponse = quidditchPlayersUseCase.fetchHousesApi()

            // verify
            coVerify(exactly = 0) { mockQuidditchPlayersRepository.createAllHousesToDB(MockDataFromCPP.getMockHouses()) }
            assertEquals(
                MockData.mockHousesGenericErrorResponseWrapper,
                housesApiResponse,
            )
        }

    @Test
    fun fetchHousesApi_throw_exception_at_createAllHousesToDB_error_flow() =
        runTest {
            // when
            coEvery { mockQuidditchPlayersRepository.getAllHouses() } returns MockData.mockHousesResponseWrapper
            coEvery { mockQuidditchPlayersRepository.createAllHousesToDB(MockData.mockHouses) } throws RuntimeException("Some error")

            // then
            setupQuidditchPlayersUseCase()
            val housesApiResponse = quidditchPlayersUseCase.fetchHousesApi()

            // verify
            assertEquals(
                MockData.mockHousesGenericErrorResponseWrapper,
                housesApiResponse,
            )
        }

    @Test
    fun fetchPlayersAndPositionsApis_happy_success_flow() =
        runTest {
            // when
            coEvery { mockQuidditchPlayersRepository.fetchPlayersAndPositions(HouseName.RAVENCLAW.name) } returns MockData.mockRavenclawPlayersAndPositionsResponseWrappers
            coEvery { mockQuidditchPlayersRepository.createPlayersByHouseToDB(MockData.ravenclawTeam().toPlayersEntities(MockData.mockPositions)) } returns Unit

            // then
            setupQuidditchPlayersUseCase()
            val playersAndPositionsApisResponse = quidditchPlayersUseCase.fetchPlayersAndPositionsApis(HouseName.RAVENCLAW.name)

            // verify
            coVerify { mockQuidditchPlayersRepository.createPlayersByHouseToDB(MockData.ravenclawTeam().toPlayersEntities(MockData.mockPositions)) }
            assertEquals(
                MockData.mockTrueResponseWrapper,
                playersAndPositionsApisResponse,
            )
        }

    @Test
    fun fetchPlayersAndPositionsApis_with_players_error_response_error_flow() =
        runTest {
            // when
            coEvery { mockQuidditchPlayersRepository.fetchPlayersAndPositions(HouseName.RAVENCLAW.name) } returns MockData.mockRavenclawPlayersErrorAndPositionsResponseWrappers

            // then
            setupQuidditchPlayersUseCase()
            val playersAndPositionsApisResponse = quidditchPlayersUseCase.fetchPlayersAndPositionsApis(HouseName.RAVENCLAW.name)

            // verify
            coVerify(exactly = 0) { mockQuidditchPlayersRepository.createPlayersByHouseToDB(MockData.ravenclawTeam().toPlayersEntities(MockData.mockPositions)) }
            assertEquals(
                MockData.mockBooleanResponseWrapperGenericError,
                playersAndPositionsApisResponse,
            )
        }

    @Test
    fun fetchPlayersAndPositionsApis_with_positions_error_response_error_flow() =
        runTest {
            // when
            coEvery { mockQuidditchPlayersRepository.fetchPlayersAndPositions(HouseName.RAVENCLAW.name) } returns MockData.mockRavenclawPlayersAndPositionsErrorResponseWrappers

            // then
            setupQuidditchPlayersUseCase()
            val playersAndPositionsApisResponse = quidditchPlayersUseCase.fetchPlayersAndPositionsApis(HouseName.RAVENCLAW.name)

            // verify
            coVerify(exactly = 0) { mockQuidditchPlayersRepository.createPlayersByHouseToDB(MockData.ravenclawTeam().toPlayersEntities(MockData.mockPositions)) }
            assertEquals(
                MockData.mockBooleanResponseWrapperGenericError,
                playersAndPositionsApisResponse,
            )
        }

    @Test
    fun fetchPlayersAndPositionsApis_throw_exception_at_createAllHousesToDB_error_flow() =
        runTest {
            // when
            coEvery { mockQuidditchPlayersRepository.fetchPlayersAndPositions(HouseName.RAVENCLAW.name) } returns MockData.mockRavenclawPlayersAndPositionsResponseWrappers
            coEvery { mockQuidditchPlayersRepository.createPlayersByHouseToDB(MockData.ravenclawTeam().toPlayersEntities(MockData.mockPositions)) } throws RuntimeException("Some error")

            // then
            setupQuidditchPlayersUseCase()
            val playersAndPositionsApisResponse = quidditchPlayersUseCase.fetchPlayersAndPositionsApis(HouseName.RAVENCLAW.name)

            // verify
            coVerify(exactly = 1) { mockQuidditchPlayersRepository.createPlayersByHouseToDB(MockData.ravenclawTeam().toPlayersEntities(MockData.mockPositions)) }
            assertEquals(
                MockData.mockBooleanResponseWrapperGenericError,
                playersAndPositionsApisResponse,
            )
        }

    @Test
    fun getAllPlayersToDB_happy_success_flow() =
        runTest {
            // given
            val mockGetAllPlayersPlayerEntityFlow: Flow<List<PlayerEntity>> =
                flow {
                    emit(MockData.ravenclawTeam().toPlayersEntities(MockData.mockPositions))
                }

            // when
            coEvery { mockQuidditchPlayersRepository.getAllPlayersFlow() } returns mockGetAllPlayersPlayerEntityFlow

            // then
            setupQuidditchPlayersUseCase()
            quidditchPlayersUseCase.getAllPlayersToDB { players ->

                // verify
                assertEquals(
                    MockData.mockRavenclawPlayersEntitiesResponseWrapper,
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
                    MockData.mockRavenclawPlayersEntitiesResponseWrapperError,
                    players,
                )
            }
        }

    @Test
    fun fetchStatusByHouseName_happy_success_flow() =
        runTest {
            // when
            coEvery { mockQuidditchPlayersRepository.fetchStatusByHouseName(HouseName.RAVENCLAW.name) } returns MockData.mockStatusResponseWrapper

            // then
            setupQuidditchPlayersUseCase()
            val status = quidditchPlayersUseCase.fetchStatusByHouseName(HouseName.RAVENCLAW.name)

            // verify
            assertEquals(
                MockData.mockStatusResponseWrapper,
                status,
            )
        }

    @Test
    fun fetchStatusByPlayerId_happy_success_flow() =
        runTest {
            // given
            val playerId = MockData.ravenclawTeam().first().id.toString()

            // when
            coEvery { mockQuidditchPlayersRepository.fetchStatusByPlayerId(playerId) } returns MockData.mockStatusResponseWrapper

            // then
            setupQuidditchPlayersUseCase()
            val status = quidditchPlayersUseCase.fetchStatusByPlayerId(playerId)

            // verify
            assertEquals(
                MockData.mockStatusResponseWrapper,
                status,
            )
        }

    @Test
    fun set_currentPlayer() {
        val playerState =
            MockData
                .ravenclawTeam()
                .first()
                .toPlayerEntity(MockData.mockPositions)
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
