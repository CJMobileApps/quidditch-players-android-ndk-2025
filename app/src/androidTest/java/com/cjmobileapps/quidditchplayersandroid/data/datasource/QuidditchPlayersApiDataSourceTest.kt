package com.cjmobileapps.quidditchplayersandroid.data.datasource

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cjmobileapps.quidditchplayersandroid.data.MockDataFromCPP
import com.cjmobileapps.quidditchplayersandroid.data.model.HouseName
import com.cjmobileapps.quidditchplayersandroid.network.QuidditchPlayersApi
import com.cjmobileapps.quidditchplayersandroid.testutil.BaseAndroidTest
import com.cjmobileapps.quidditchplayersandroid.util.TestCoroutineDispatchers
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QuidditchPlayersApiDataSourceTest : BaseAndroidTest() {
    private lateinit var quidditchPlayersApiDataSource: QuidditchPlayersApiDataSource

    @MockK
    lateinit var mockQuidditchPlayersApi: QuidditchPlayersApi

    private fun setupQuidditchPlayersApiDataSource() {
        quidditchPlayersApiDataSource =
            QuidditchPlayersApiDataSource(
                quidditchPlayersApi = mockQuidditchPlayersApi,
                coroutineDispatchers = TestCoroutineDispatchers,
            )
    }

    @Test
    fun housesHappySuccessFlow() =
        runTest {
            // when
            coEvery { mockQuidditchPlayersApi.getAllHouses() } returns MockDataFromCPP.mockHousesDeferredResponseSuccess

            // then
            setupQuidditchPlayersApiDataSource()
            val houses = quidditchPlayersApiDataSource.getAllHouses()

            // verify
            assertEquals(
                MockDataFromCPP.getMockHousesResponseWrapper(),
                houses,
            )
        }

    @Test
    fun getPlayersByHouseSuccessFlow() =
        runTest {
            // when
            coEvery { mockQuidditchPlayersApi.getPlayersByHouse(houseName = HouseName.RAVENCLAW.name) } returns MockDataFromCPP.mockRavenclawPlayersDeferredResponseSuccess

            // then
            setupQuidditchPlayersApiDataSource()
            val players = quidditchPlayersApiDataSource.getPlayersByHouse(HouseName.RAVENCLAW.name)

            // verify
            assertEquals(
                MockDataFromCPP.mockRavenclawPlayersResponseWrapper,
                players,
            )
        }

    @Test
    fun fetchPlayersAndPositionsSuccessFlow() =
        runTest {
            // when
            coEvery { mockQuidditchPlayersApi.getPlayersByHouse(houseName = HouseName.RAVENCLAW.name) } returns MockDataFromCPP.mockRavenclawPlayersDeferredResponseSuccess
            coEvery { mockQuidditchPlayersApi.getPositions() } returns MockDataFromCPP.mockPositionsDeferredResponseSuccess

            // then
            setupQuidditchPlayersApiDataSource()
            val playersAndPositions = quidditchPlayersApiDataSource.fetchPlayersAndPositions(HouseName.RAVENCLAW.name)

            // verify
            assertEquals(
                MockDataFromCPP.mockRavenclawPlayersAndPositionsResponseWrappers,
                playersAndPositions,
            )
        }

    @Test
    fun fetchStatusByHouseNameSuccessFlow() =
        runTest {
            // when
            coEvery { mockQuidditchPlayersApi.getStatusByHouseName(houseName = HouseName.RAVENCLAW.name) } returns MockDataFromCPP.mockStatusDeferredResponseSuccess

            // then
            setupQuidditchPlayersApiDataSource()
            val status = quidditchPlayersApiDataSource.fetchStatusByHouseName(houseName = HouseName.RAVENCLAW.name)

            // verify
            assertEquals(
                MockDataFromCPP.mockStatusResponseWrapper,
                status,
            )
        }

    @Test
    fun fetchStatusByPlayerIdSuccessFlow() =
        runTest {
            // given
            val playerId = MockDataFromCPP.getRavenclawTeam().first().id.toString()

            // when
            coEvery { mockQuidditchPlayersApi.fetchStatusByPlayerId(playerId = playerId) } returns MockDataFromCPP.mockStatusDeferredResponseSuccess

            // then
            setupQuidditchPlayersApiDataSource()
            val status = quidditchPlayersApiDataSource.fetchStatusByPlayerId(playerId = playerId)

            // verify
            assertEquals(
                MockDataFromCPP.mockStatusResponseWrapper,
                status,
            )
        }
}
