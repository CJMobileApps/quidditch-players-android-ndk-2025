package com.cjmobileapps.quidditchplayersandroid.ui.playerslist.viewmodel

import com.cjmobileapps.quidditchplayersandroid.data.model.PlayerState

interface PlayersListViewModel {

    fun getState(): PlayersListViewModelImpl.PlayersListState

    fun getSnackbarState(): PlayersListViewModelImpl.PlayersListSnackbarState

    fun resetSnackbarState()

    fun resetNavRouteUiToIdle()

    fun goToPlayerDetailUi(player: PlayerState)

    fun getPlayersListNavRouteUiState(): PlayersListViewModelImpl.PlayersListNavRouteUi

    fun getTopBarTitle(): String
}
