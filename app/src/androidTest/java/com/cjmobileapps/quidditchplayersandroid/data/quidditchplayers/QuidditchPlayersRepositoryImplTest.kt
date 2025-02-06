package com.cjmobileapps.quidditchplayersandroid.data.quidditchplayers

import com.cjmobileapps.quidditchplayersandroid.data.MockDataFromCPP
import com.cjmobileapps.quidditchplayersandroid.data.datasource.QuidditchPlayersApiDataSource
import com.cjmobileapps.quidditchplayersandroid.data.datasource.QuidditchPlayersLocalDataSource
import com.cjmobileapps.quidditchplayersandroid.data.model.House
import com.cjmobileapps.quidditchplayersandroid.data.model.HouseName
import com.cjmobileapps.quidditchplayersandroid.data.model.PlayerEntity
import com.cjmobileapps.quidditchplayersandroid.testutil.BaseAndroidTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test

class QuidditchPlayersRepositoryImplTest : BaseAndroidTest() {
    private lateinit var quidditchPlayersRepositoryImpl: QuidditchPlayersRepositoryImpl

    @MockK
    private lateinit var mockQuidditchPlayersApiDataSource: QuidditchPlayersApiDataSource

    @MockK
    private lateinit var mockQuidditchPlayersLocalDataSource: QuidditchPlayersLocalDataSource

    private fun setupQuidditchPlayersRepositoryImpl() {
        quidditchPlayersRepositoryImpl =
            QuidditchPlayersRepositoryImpl(
                quidditchPlayersApiDataSource = mockQuidditchPlayersApiDataSource,
                quidditchPlayersLocalDataSource = mockQuidditchPlayersLocalDataSource,
            )
    }

    @Test
    fun getAllHousesHappySuccessFlow() =
        runTest {
            // Given
            coEvery { mockQuidditchPlayersApiDataSource.getAllHouses() } returns MockDataFromCPP.getMockHousesResponseWrapper()

            // then
            setupQuidditchPlayersRepositoryImpl()
            val houses = quidditchPlayersRepositoryImpl.getAllHouses()

            // verify
            assertEquals(
                MockDataFromCPP.getMockHousesResponseWrapper(),
                houses,
            )
        }

    @Test
    fun getAllHousesFlowHappySuccessFlow() =
        runTest {
            // given
            val mockGetAllHousesFlow: Flow<List<House>> =
                flow {
                    emit(MockDataFromCPP.getMockHouses())
                }
            coEvery { mockQuidditchPlayersLocalDataSource.getAllHousesFlow() } returns mockGetAllHousesFlow

            // then
            setupQuidditchPlayersRepositoryImpl()
            val houses = quidditchPlayersRepositoryImpl.getAllHousesFlow().first()

            // verify
            assertEquals(
                MockDataFromCPP.getMockHouses(),
                houses,
            )
        }

    @Test
    fun createPlayersByHouseToDBHappySuccessFlow() =
        runTest {
            // When
            coEvery { mockQuidditchPlayersLocalDataSource.createPlayersByHouseToDB(MockDataFromCPP.mockPlayersEntities) } returns Unit

            // then
            setupQuidditchPlayersRepositoryImpl()
            quidditchPlayersRepositoryImpl.createPlayersByHouseToDB(MockDataFromCPP.mockPlayersEntities)

            // verify
            coVerify { mockQuidditchPlayersLocalDataSource.createPlayersByHouseToDB(MockDataFromCPP.mockPlayersEntities) }
        }

    @Test
    fun getPlayersByHouseHappySuccessFlow() =
        runTest {
            // given
            coEvery { mockQuidditchPlayersApiDataSource.getPlayersByHouse(HouseName.RAVENCLAW.name) } returns MockDataFromCPP.mockRavenclawPlayersResponseWrapper

            // then
            setupQuidditchPlayersRepositoryImpl()
            val players = quidditchPlayersRepositoryImpl.getPlayersByHouse(HouseName.RAVENCLAW.name)

            // verify
            assertEquals(
                MockDataFromCPP.mockRavenclawPlayersResponseWrapper,
                players,
            )
        }

    @Test
    fun createAllHousesToDB_HappySuccessFlow() =
        runTest {
            // when
            coEvery { mockQuidditchPlayersLocalDataSource.createAllHouses(MockDataFromCPP.getMockHouses()) } returns Unit

            // then
            setupQuidditchPlayersRepositoryImpl()
            quidditchPlayersRepositoryImpl.createAllHousesToDB(MockDataFromCPP.getMockHouses())

            // verify
            coVerify { mockQuidditchPlayersLocalDataSource.createAllHouses(MockDataFromCPP.getMockHouses()) }
        }

    @Test
    fun getAllPlayersFlow_happy_success_flow() =
        runTest {
            val mockGetAllPlayersEntitiesFlow: Flow<List<PlayerEntity>> =
                flow {
                    emit(MockDataFromCPP.mockPlayersEntities)
                }
            coEvery { mockQuidditchPlayersLocalDataSource.getAllPlayersFlow() } returns mockGetAllPlayersEntitiesFlow

            // then
            setupQuidditchPlayersRepositoryImpl()
            val players = quidditchPlayersRepositoryImpl.getAllPlayersFlow().first()

            // verify
            assertEquals(
                MockDataFromCPP.mockPlayersEntities,
                players,
            )
        }


    @Test
    fun fetchStatusByHouseName_happy_success_flow() =
        runTest {
            // when
            coEvery { mockQuidditchPlayersApiDataSource.fetchStatusByHouseName(HouseName.RAVENCLAW.name) } returns MockDataFromCPP.getResponseWrapperMockStatus()

            // then
            setupQuidditchPlayersRepositoryImpl()
            val status = quidditchPlayersRepositoryImpl.fetchStatusByHouseName(HouseName.RAVENCLAW.name)

            // verify
            assertEquals(
                MockDataFromCPP.getResponseWrapperMockStatus(),
                status,
            )
        }

    @Test
    fun fetchStatusByPlayerId_happy_success_flow() =
        runTest {
            // given
            val playerId = MockDataFromCPP.getRavenclawTeam().first().id.toString()

            // when
            coEvery { mockQuidditchPlayersApiDataSource.fetchStatusByPlayerId(playerId) } returns MockDataFromCPP.getResponseWrapperMockStatus()

            // then
            setupQuidditchPlayersRepositoryImpl()
            val status = quidditchPlayersRepositoryImpl.fetchStatusByPlayerId(playerId = playerId)

            // verify
            assertEquals(
                MockDataFromCPP.getResponseWrapperMockStatus(),
                status,
            )
        }
}
