package com.cjmobileapps.quidditchplayersandroid.ui.houses.viewmodel

interface HousesViewModel {
    fun getState(): HousesViewModelImpl.HousesState

    fun getSnackbarState(): HousesViewModelImpl.HousesSnackbarState

    fun getHousesNavRouteUiState(): HousesViewModelImpl.HousesNavRouteUi

    fun resetSnackbarState()

    fun resetNavRouteUiToIdle()

    fun goToPlayersListUi(houseName: String)
}
