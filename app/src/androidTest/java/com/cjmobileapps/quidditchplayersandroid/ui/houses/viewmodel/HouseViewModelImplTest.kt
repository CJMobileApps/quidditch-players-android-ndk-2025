package com.cjmobileapps.quidditchplayersandroid.ui.houses.viewmodel

import com.cjmobileapps.quidditchplayersandroid.data.MockDataFromCPP
import com.cjmobileapps.quidditchplayersandroid.data.model.House
import com.cjmobileapps.quidditchplayersandroid.data.model.HouseName
import com.cjmobileapps.quidditchplayersandroid.data.model.ResponseWrapper
import com.cjmobileapps.quidditchplayersandroid.data.quidditchplayers.QuidditchPlayersUseCase
import com.cjmobileapps.quidditchplayersandroid.testutil.BaseAndroidTest
import com.cjmobileapps.quidditchplayersandroid.util.TestCoroutineDispatchers
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.invoke
import io.mockk.slot
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test

class HouseViewModelImplTest : BaseAndroidTest() {
    private lateinit var housesViewModel: HousesViewModelImpl

    @MockK
    lateinit var mockQuidditchPlayersUseCase: QuidditchPlayersUseCase

    private val housesResponseWrapperArgumentCaptor = slot<(ResponseWrapper<List<House>>) -> Unit>()

    private fun setupHouseViewModel() {
        housesViewModel =
            HousesViewModelImpl(
                quidditchPlayersUseCase = mockQuidditchPlayersUseCase,
                coroutineDispatchers = TestCoroutineDispatchers,
            )
    }

    @Test
    fun houseViewmodelApisNeverInit() {
        // then
        setupHouseViewModel()
        housesViewModel.goToPlayersListUi(HouseName.RAVENCLAW.name)
        var state = housesViewModel.getState()

        // verify
        assertTrue(state is HousesViewModelImpl.HousesState.LoadingState)

        // then
        var housesNavRouteUi = housesViewModel.getHousesNavRouteUiState()
        state = housesViewModel.getState()

        // verify
        assertTrue(state is HousesViewModelImpl.HousesState.LoadingState)
        assertTrue(housesNavRouteUi is HousesViewModelImpl.HousesNavRouteUi.Idle)

        // then
        housesViewModel.resetNavRouteUiToIdle()
        housesNavRouteUi = housesViewModel.getHousesNavRouteUiState()
        state = housesViewModel.getState()
        assertTrue(state is HousesViewModelImpl.HousesState.LoadingState)
        assertTrue(housesNavRouteUi is HousesViewModelImpl.HousesNavRouteUi.Idle)
    }

    @Test
    fun fetchHousesThenGoToPlayerListHappyFlow() =
        runTest {
            // then init setup
            setupHouseViewModel()
            var housesState = housesViewModel.getState()

            // verify
            assertTrue(housesState is HousesViewModelImpl.HousesState.LoadingState)

            // when
            coEvery { mockQuidditchPlayersUseCase.fetchHousesApi() } returns MockDataFromCPP.getMockTrueResponseWrapper()
            coEvery { mockQuidditchPlayersUseCase.getHousesFromDB(capture(housesResponseWrapperArgumentCaptor)) }

            // then
            setupHouseViewModel()
            housesResponseWrapperArgumentCaptor.invoke(MockDataFromCPP.getMockHousesResponseWrapper())
            housesState = housesViewModel.getState()

            // verify
            assertTrue((housesState is HousesViewModelImpl.HousesState.HousesLoadedState))
            if (housesState !is HousesViewModelImpl.HousesState.HousesLoadedState) return@runTest

            assertEquals(
                MockDataFromCPP.getMockHouses(),
                housesState.houses,
            )
            assertTrue(housesState.housesNavRouteUi.value is HousesViewModelImpl.HousesNavRouteUi.Idle)

            // then
            housesViewModel.goToPlayersListUi(HouseName.RAVENCLAW.name)

            // verify
            assertTrue(housesState.housesNavRouteUi.value is HousesViewModelImpl.HousesNavRouteUi.GoToPlayerListUi)
            val housesNavRouteUi = (housesState.housesNavRouteUi.value as HousesViewModelImpl.HousesNavRouteUi.GoToPlayerListUi)
            assertEquals(
                HouseName.RAVENCLAW.name,
                housesNavRouteUi.houseName,
            )
            assertEquals(
                "nav_players_list/RAVENCLAW",
                housesNavRouteUi.getNavRouteWithArguments(),
            )

            // then
            housesViewModel.resetNavRouteUiToIdle()

            // verify
            assertTrue(housesViewModel.getHousesNavRouteUiState() is HousesViewModelImpl.HousesNavRouteUi.Idle)
        }

    @Test
    fun fetchHousesThenThrowErrorAtFetchHousesApiErrorResponseFlow() =
        runTest {
            // then init setup
            setupHouseViewModel()
            var housesState = housesViewModel.getState()

            // verify
            assertTrue((housesState is HousesViewModelImpl.HousesState.LoadingState))

            // when
            coEvery { mockQuidditchPlayersUseCase.fetchHousesApi() } returns MockDataFromCPP.getMockBooleanResponseWrapperGenericError()
            coEvery { mockQuidditchPlayersUseCase.getHousesFromDB(capture(housesResponseWrapperArgumentCaptor)) } returns Unit

            // then
            setupHouseViewModel()
            housesResponseWrapperArgumentCaptor.invoke(MockDataFromCPP.getMockHousesResponseWrapper())
            housesState = housesViewModel.getState()
            var snackbarState = housesViewModel.getSnackbarState()

            // verify
            assertTrue(housesState is HousesViewModelImpl.HousesState.HousesLoadedState)
            if ((housesState !is HousesViewModelImpl.HousesState.HousesLoadedState)) return@runTest
            assertTrue(snackbarState is HousesViewModelImpl.HousesSnackbarState.UnableToGetHousesListError)
            if (snackbarState !is HousesViewModelImpl.HousesSnackbarState.UnableToGetHousesListError) return@runTest
            assertEquals(
                MockDataFromCPP.getMockHouses(),
                housesState.houses,
            )
            assertTrue(housesState.housesNavRouteUi.value is HousesViewModelImpl.HousesNavRouteUi.Idle)
            assertNull(snackbarState.error)

            // then
            housesViewModel.resetSnackbarState()
            snackbarState = housesViewModel.getSnackbarState()

            // verify
            assertTrue(snackbarState is HousesViewModelImpl.HousesSnackbarState.Idle)
        }

    @Test
    fun fetchHousesThenThrowErrorAtGetHousesFromDBErrorResponseFlow() =
        runTest {
            // then init setup
            setupHouseViewModel()
            var housesState = housesViewModel.getState()

            // verify
            assertTrue((housesState is HousesViewModelImpl.HousesState.LoadingState))

            // when
            coEvery { mockQuidditchPlayersUseCase.fetchHousesApi() } returns MockDataFromCPP.getMockTrueResponseWrapper()
            coEvery { mockQuidditchPlayersUseCase.getHousesFromDB(capture(housesResponseWrapperArgumentCaptor)) } returns Unit

            // then
            setupHouseViewModel()
            housesResponseWrapperArgumentCaptor.invoke(MockDataFromCPP.mockHousesGenericErrorResponseWrapper)
            housesState = housesViewModel.getState()
            var snackbarState = housesViewModel.getSnackbarState()

            // verify
            assertTrue(housesState is HousesViewModelImpl.HousesState.HousesLoadedState)
            if ((housesState !is HousesViewModelImpl.HousesState.HousesLoadedState)) return@runTest
            assertTrue(snackbarState is HousesViewModelImpl.HousesSnackbarState.UnableToGetHousesListError)
            if ((snackbarState !is HousesViewModelImpl.HousesSnackbarState.UnableToGetHousesListError)) return@runTest
            assertEquals(
                emptyList<List<House>>(),
                housesState.houses,
            )
            assertTrue(housesState.housesNavRouteUi.value is HousesViewModelImpl.HousesNavRouteUi.Idle)
            assertNull(snackbarState.error)

            // then
            housesViewModel.resetSnackbarState()
            snackbarState = housesViewModel.getSnackbarState()

            // verify
            assertTrue(snackbarState is HousesViewModelImpl.HousesSnackbarState.Idle)
        }

    @Test
    fun fetchHousesThenThrowExceptionAtFetchHousesApiErrorResponseFlow() =
        runTest {
            // then init setup
            setupHouseViewModel()
            var housesState = housesViewModel.getState()

            // verify
            assertTrue((housesState is HousesViewModelImpl.HousesState.LoadingState))

            // when
            coEvery { mockQuidditchPlayersUseCase.fetchHousesApi() } throws Exception("Some error")
            coEvery { mockQuidditchPlayersUseCase.getHousesFromDB(capture(housesResponseWrapperArgumentCaptor)) } returns Unit

            // then
            setupHouseViewModel()
            housesResponseWrapperArgumentCaptor.invoke(MockDataFromCPP.getMockHousesResponseWrapper())
            housesState = housesViewModel.getState()
            var snackbarState = housesViewModel.getSnackbarState()

            // verify
            assertTrue(housesState is HousesViewModelImpl.HousesState.HousesLoadedState)
            if ((housesState !is HousesViewModelImpl.HousesState.HousesLoadedState)) return@runTest
            assertTrue(snackbarState is HousesViewModelImpl.HousesSnackbarState.ShowGenericError)
            if ((snackbarState !is HousesViewModelImpl.HousesSnackbarState.ShowGenericError)) return@runTest
            assertEquals(
                MockDataFromCPP.getMockHouses(),
                housesState.houses,
            )
            assertTrue(housesState.housesNavRouteUi.value is HousesViewModelImpl.HousesNavRouteUi.Idle)
            assertNull(snackbarState.error)

            // then
            housesViewModel.resetSnackbarState()
            snackbarState = housesViewModel.getSnackbarState()

            // verify
            assertTrue(snackbarState is HousesViewModelImpl.HousesSnackbarState.Idle)
        }
}
