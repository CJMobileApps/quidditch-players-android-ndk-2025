package com.cjmobileapps.quidditchplayersandroid.ui.playerdetail.viewmodel

interface PlayerDetailViewModel {
    fun getState(): PlayerDetailViewModelImpl.PlayerDetailState

    fun resetSnackbarState()

    fun getSnackbarState(): PlayerDetailViewModelImpl.PlayerDetailSnackbarState

    fun getTopBarTitle(): String
}
