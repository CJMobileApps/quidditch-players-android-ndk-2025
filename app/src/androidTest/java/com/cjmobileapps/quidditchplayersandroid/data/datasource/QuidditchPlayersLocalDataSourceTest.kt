package com.cjmobileapps.quidditchplayersandroid.data.datasource

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cjmobileapps.quidditchplayersandroid.data.MockData
import com.cjmobileapps.quidditchplayersandroid.data.MockDataFromCPP
import com.cjmobileapps.quidditchplayersandroid.data.model.House
import com.cjmobileapps.quidditchplayersandroid.data.model.PlayerEntity
import com.cjmobileapps.quidditchplayersandroid.room.QuidditchPlayersDao
import com.cjmobileapps.quidditchplayersandroid.testutil.BaseAndroidTest
import com.cjmobileapps.quidditchplayersandroid.util.TestCoroutineDispatchers
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QuidditchPlayersLocalDataSourceTest : BaseAndroidTest() {
    private lateinit var quidditchPlayersLocalDataSource: QuidditchPlayersLocalDataSource

    @MockK
    lateinit var mockQuidditchPlayersDao: QuidditchPlayersDao

    private fun setupQuidditchPlayersLocalDataSource() {
        quidditchPlayersLocalDataSource =
            QuidditchPlayersLocalDataSource(
                quidditchPlayersDao = mockQuidditchPlayersDao,
                coroutineDispatchers = TestCoroutineDispatchers,
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
            coEvery { mockQuidditchPlayersDao.getAllHouses() } returns mockGetAllHousesFlow

            // then
            setupQuidditchPlayersLocalDataSource()
            val houses = quidditchPlayersLocalDataSource.getAllHousesFlow().first()

            // verify
            assertEquals(
                MockDataFromCPP.getMockHouses(),
                houses,
            )
        }

    @Test
    fun createAllHousesHappySuccessFlow() =
        runTest {
            // given
            every { mockQuidditchPlayersDao.deleteAllHouses() } returns Unit
            every { mockQuidditchPlayersDao.insertAllHouses(MockDataFromCPP.getMockHouses()) } returns Unit

            // then
            setupQuidditchPlayersLocalDataSource()
            quidditchPlayersLocalDataSource.createAllHouses(MockDataFromCPP.getMockHouses())

            verify { mockQuidditchPlayersDao.deleteAllHouses() }
            verify { mockQuidditchPlayersDao.insertAllHouses(MockDataFromCPP.getMockHouses()) }
        }

    @Test
    fun getAllPlayersFlowHappySuccessFlow() =
        runTest {
            // given
            val mockGetAllPlayersEntitiesFlow: Flow<List<PlayerEntity>> =
                flow {
                    emit(MockData.mockPlayersEntities)
                }
            every { mockQuidditchPlayersDao.getAllPlayers() } returns mockGetAllPlayersEntitiesFlow

            // then
            setupQuidditchPlayersLocalDataSource()
            val players = quidditchPlayersLocalDataSource.getAllPlayersFlow().first()

            // verify
            assertEquals(
                MockData.mockPlayersEntities,
                players,
            )
        }

    @Test
    fun createPlayersByHouseToDbHappySuccessFlow() =
        runTest {
            // given
            every { mockQuidditchPlayersDao.insertAllPlayers(MockData.mockPlayersEntities) } returns Unit
            every { mockQuidditchPlayersDao.deleteAllPlayers() } returns Unit

            // then
            setupQuidditchPlayersLocalDataSource()
            quidditchPlayersLocalDataSource.createPlayersByHouseToDB(MockData.mockPlayersEntities)

            // verify
            verify { mockQuidditchPlayersDao.deleteAllPlayers() }
            verify { mockQuidditchPlayersDao.insertAllPlayers(MockData.mockPlayersEntities) }
        }
}
