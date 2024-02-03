package com.cjmobileapps.quidditchplayersandroid.ui.houses.viewmodel

interface HousesViewModel {

    fun getState(): HousesViewModelImpl.HousesState

    fun getSnackbarState(): HousesViewModelImpl.HousesSnackbarState

    fun resetSnackbarState()
}
