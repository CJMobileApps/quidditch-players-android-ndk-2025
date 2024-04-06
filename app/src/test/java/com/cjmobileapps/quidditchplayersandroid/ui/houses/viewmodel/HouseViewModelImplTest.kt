package com.cjmobileapps.quidditchplayersandroid.ui.houses.viewmodel

import com.cjmobileapps.quidditchplayersandroid.data.MockData
import com.cjmobileapps.quidditchplayersandroid.data.model.House
import com.cjmobileapps.quidditchplayersandroid.data.model.HouseName
import com.cjmobileapps.quidditchplayersandroid.data.model.ResponseWrapper
import com.cjmobileapps.quidditchplayersandroid.data.quidditchplayers.QuidditchPlayersUseCase
import com.cjmobileapps.quidditchplayersandroid.testutil.BaseTest
import com.cjmobileapps.quidditchplayersandroid.testutil.TestCoroutineDispatchers
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.given

class HouseViewModelImplTest : BaseTest() {
    private lateinit var housesViewModel: HousesViewModelImpl

    @Mock
    lateinit var mockQuidditchPlayersUseCase: QuidditchPlayersUseCase

    private val housesResponseWrapperArgumentCaptor =
        argumentCaptor<(ResponseWrapper<List<House>>) -> Unit>()

    private fun setupHouseViewModel() {
        housesViewModel =
            HousesViewModelImpl(
                quidditchPlayersUseCase = mockQuidditchPlayersUseCase,
                coroutineDispatchers = TestCoroutineDispatchers,
            )
    }

    @Test
    fun `fetch houses then go to player list happy flow`() =
        runTest {
            // then init setup
            setupHouseViewModel()
            var housesState = housesViewModel.getState()

            // verify
            Assertions.assertTrue((housesState is HousesViewModelImpl.HousesState.LoadingState))

            // when
            Mockito.`when`(mockQuidditchPlayersUseCase.fetchHousesApi()).thenReturn(MockData.mockTrueResponseWrapper)
            Mockito.`when`(mockQuidditchPlayersUseCase.getHousesFromDB(housesResponseWrapperArgumentCaptor.capture())).thenReturn(Unit)

            // then
            setupHouseViewModel()
            housesResponseWrapperArgumentCaptor.firstValue.invoke(MockData.mockHousesResponseWrapper)
            housesState = housesViewModel.getState()

            // verify
            Assertions.assertTrue((housesState is HousesViewModelImpl.HousesState.HousesLoadedState))
            if ((housesState !is HousesViewModelImpl.HousesState.HousesLoadedState)) return@runTest

            Assertions.assertEquals(
                MockData.mockHouses,
                housesState.houses,
            )
            Assertions.assertTrue(housesState.housesNavRouteUi.value is HousesViewModelImpl.HousesNavRouteUi.Idle)

            // then
            housesViewModel.goToPlayersListUi(HouseName.RAVENCLAW.name)

            // verify
            Assertions.assertTrue(housesState.housesNavRouteUi.value is HousesViewModelImpl.HousesNavRouteUi.GoToPlayerListUi)

            // then
            housesViewModel.resetNavRouteUiToIdle()

            // verify
            Assertions.assertTrue(housesState.housesNavRouteUi.value is HousesViewModelImpl.HousesNavRouteUi.Idle)
        }

    @Test
    fun `fetch houses the throw error at fetchHousesApi() error response flow`() =
        runTest {
            // then init setup
            setupHouseViewModel()
            var housesState = housesViewModel.getState()

            // verify
            Assertions.assertTrue((housesState is HousesViewModelImpl.HousesState.LoadingState))

            // when
            Mockito.`when`(mockQuidditchPlayersUseCase.fetchHousesApi()).thenReturn(MockData.mockBooleanResponseWrapperGenericError)
            Mockito.`when`(mockQuidditchPlayersUseCase.getHousesFromDB(housesResponseWrapperArgumentCaptor.capture())).thenReturn(Unit)

            // then
            setupHouseViewModel()
            housesResponseWrapperArgumentCaptor.firstValue.invoke(MockData.mockHousesResponseWrapper)
            housesState = housesViewModel.getState()
            var snackbarState = housesViewModel.getSnackbarState()

            // verify
            Assertions.assertTrue(housesState is HousesViewModelImpl.HousesState.HousesLoadedState)
            if ((housesState !is HousesViewModelImpl.HousesState.HousesLoadedState)) return@runTest
            Assertions.assertTrue(snackbarState is HousesViewModelImpl.HousesSnackbarState.UnableToGetHousesListError)
            if (snackbarState !is HousesViewModelImpl.HousesSnackbarState.UnableToGetHousesListError) return@runTest
            Assertions.assertEquals(
                MockData.mockHouses,
                housesState.houses,
            )
            Assertions.assertTrue(housesState.housesNavRouteUi.value is HousesViewModelImpl.HousesNavRouteUi.Idle)
            Assertions.assertNull(snackbarState.error)

            // then
            housesViewModel.resetSnackbarState()
            snackbarState = housesViewModel.getSnackbarState()

            // verify
            Assertions.assertTrue(snackbarState is HousesViewModelImpl.HousesSnackbarState.Idle)
        }

    @Test
    fun `fetch houses the throw error at getHousesFromDB() error response flow`() =
        runTest {
            // then init setup
            setupHouseViewModel()
            var housesState = housesViewModel.getState()

            // verify
            Assertions.assertTrue((housesState is HousesViewModelImpl.HousesState.LoadingState))

            // when
            Mockito.`when`(mockQuidditchPlayersUseCase.fetchHousesApi()).thenReturn(MockData.mockTrueResponseWrapper)
            Mockito.`when`(mockQuidditchPlayersUseCase.getHousesFromDB(housesResponseWrapperArgumentCaptor.capture())).thenReturn(Unit)

            // then
            setupHouseViewModel()
            housesResponseWrapperArgumentCaptor.firstValue.invoke(MockData.mockHousesGenericErrorResponseWrapper)
            housesState = housesViewModel.getState()
            var snackbarState = housesViewModel.getSnackbarState()

            // verify
            Assertions.assertTrue(housesState is HousesViewModelImpl.HousesState.HousesLoadedState)
            if ((housesState !is HousesViewModelImpl.HousesState.HousesLoadedState)) return@runTest
            Assertions.assertTrue(snackbarState is HousesViewModelImpl.HousesSnackbarState.UnableToGetHousesListError)
            if ((snackbarState !is HousesViewModelImpl.HousesSnackbarState.UnableToGetHousesListError)) return@runTest
            Assertions.assertEquals(
                emptyList<List<House>>(),
                housesState.houses,
            )
            Assertions.assertTrue(housesState.housesNavRouteUi.value is HousesViewModelImpl.HousesNavRouteUi.Idle)
            Assertions.assertNull(snackbarState.error)

            // then
            housesViewModel.resetSnackbarState()
            snackbarState = housesViewModel.getSnackbarState()

            // verify
            Assertions.assertTrue(snackbarState is HousesViewModelImpl.HousesSnackbarState.Idle)
        }

    @Test
    fun `fetch houses the throw exception at fetchHousesApi() error response flow`() =
        runTest {
            // then init setup
            setupHouseViewModel()
            var housesState = housesViewModel.getState()

            // verify
            Assertions.assertTrue((housesState is HousesViewModelImpl.HousesState.LoadingState))

            // when
//        Mockito.`when`(mockQuidditchPlayersUseCase.fetchHousesApi()).thenThrow(Exception("Some error"))
            given(mockQuidditchPlayersUseCase.fetchHousesApi()).willAnswer {
                Exception("Some error")
            }
            Mockito.`when`(mockQuidditchPlayersUseCase.getHousesFromDB(housesResponseWrapperArgumentCaptor.capture())).thenReturn(Unit)

            // then
            setupHouseViewModel()
            housesResponseWrapperArgumentCaptor.firstValue.invoke(MockData.mockHousesResponseWrapper)
            housesState = housesViewModel.getState()
            var snackbarState = housesViewModel.getSnackbarState()

            // verify
            Assertions.assertTrue(housesState is HousesViewModelImpl.HousesState.HousesLoadedState)
            if ((housesState !is HousesViewModelImpl.HousesState.HousesLoadedState)) return@runTest
            Assertions.assertTrue(snackbarState is HousesViewModelImpl.HousesSnackbarState.ShowGenericError)
            if ((snackbarState !is HousesViewModelImpl.HousesSnackbarState.ShowGenericError)) return@runTest
            Assertions.assertEquals(
                MockData.mockHouses,
                housesState.houses,
            )
            Assertions.assertTrue(housesState.housesNavRouteUi.value is HousesViewModelImpl.HousesNavRouteUi.Idle)
            Assertions.assertNull(snackbarState.error)

            // then
            housesViewModel.resetSnackbarState()
            snackbarState = housesViewModel.getSnackbarState()

            // verify
            Assertions.assertTrue(snackbarState is HousesViewModelImpl.HousesSnackbarState.Idle)
        }
}
